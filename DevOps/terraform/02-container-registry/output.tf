output "registry_admin_url" {
  value = azurerm_container_registry.fsa_honcharenko.login_server
}

output "registry_admin_user" {
  value = azurerm_container_registry.fsa_honcharenko.admin_username
}

output "registry_admin_pass" {
  value     = azurerm_container_registry.fsa_honcharenko.admin_password
  sensitive = true
}

output "registry_token" {
  value     = azurerm_container_registry_token.fsa_honcharenko_acr_token.id
  sensitive = true
}
