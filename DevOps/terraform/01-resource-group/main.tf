provider "azurerm" {
  features {}

  subscription_id = var.subscription_id
  tenant_id       = var.tenant_id
}

resource "azurerm_resource_group" "fsa_honcharenko" {
  name     =  var.resource_group
  location = var.location
}
