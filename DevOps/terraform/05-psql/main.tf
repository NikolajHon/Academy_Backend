provider "azurerm" {
  features {}

  # напрямую в main.tf, без отдельного variables.tf
  subscription_id = "54f6edfe-b3a6-46ee-84bf-dc9a72bf1714"
  tenant_id       = "3590242b-a92d-4bb9-9ff9-eb7a1183f511"
}

resource "azurerm_postgresql_flexible_server" "honcharenko_db" {
  name                   = "fsa-honcharenko-psql"
  resource_group_name    = "fsa_honcharenko"
  location               = "northeurope"
  version                = "16"
  administrator_login    = "fsaadmin"
  administrator_password = "P@ssword12345"
  sku_name               = "B_Standard_B1ms"
  storage_mb             = 32768
  backup_retention_days  = 7
  zone                   = 1

  tags = {
    environment = "lab"
  }

  lifecycle {
    prevent_destroy = false
  }
}

resource "azurerm_postgresql_flexible_server_database" "keycloak" {
  name      = "keycloak"
  server_id = azurerm_postgresql_flexible_server.honcharenko_db.id
  collation = "en_US.utf8"
  charset   = "utf8"
}

resource "azurerm_postgresql_flexible_server_database" "app" {
  name      = "fsa-db"
  server_id = azurerm_postgresql_flexible_server.honcharenko_db.id
  collation = "sk_SK.utf8"
  charset   = "UTF8"

  lifecycle {
    prevent_destroy = false
  }
}

resource "azurerm_postgresql_flexible_server_firewall_rule" "allow_all" {
  name             = "allow_all"
  server_id        = azurerm_postgresql_flexible_server.honcharenko_db.id
  start_ip_address = "0.0.0.0"
  end_ip_address   = "255.255.255.255"
}
