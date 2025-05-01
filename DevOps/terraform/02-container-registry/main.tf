provider "azurerm" {
  features {}

  subscription_id = var.subscription_id
  tenant_id       = var.tenant_id
}

# -----------------------------------------------------------------------------

resource "azurerm_container_registry" "fsa_honcharenko" {
  name                   = "${var.resource_name}${var.registry_name}"
  resource_group_name    = var.resource_group
  location               = var.location
  sku                    = "Standard"
  admin_enabled          = true
  anonymous_pull_enabled = "false"


  tags = {
    environment = var.environment
  }
}

resource "azurerm_container_registry_scope_map" "fsa_honcharenko_sm" {
  name                    = "${var.resource_name}-scope-map"
  container_registry_name = azurerm_container_registry.fsa_honcharenko.name
  resource_group_name     = var.resource_group
  actions = [
    "repositories/fsa/content/read",
    "repositories/fsa/content/write"
  ]
}

resource "azurerm_container_registry_token" "fsa_honcharenko_acr_token" {
  name                    = "${var.resource_name}registrytoken"
  container_registry_name = azurerm_container_registry.fsa_honcharenko.name
  resource_group_name     = var.resource_group
  scope_map_id            = azurerm_container_registry_scope_map.fsa_honcharenko_sm.id
}
