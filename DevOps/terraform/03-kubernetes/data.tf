data "azurerm_container_registry" "fsa_container_registry" {
  name                = "fsahoncharenkoregistry"
  resource_group_name = "fsa_honcharenko"
}