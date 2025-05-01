terraform {
  required_version = "= 1.11.4"

  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "= 4.26.0"
    }
  }

  backend "azurerm" {
    resource_group_name  = "honcharenko-fsatfstate"
    storage_account_name = "honcharenkofsatfstate"
    container_name       = "fsa-infrastructure"
    key                  = "rg/tfstate/terraform.tfstate"
  }
}
 