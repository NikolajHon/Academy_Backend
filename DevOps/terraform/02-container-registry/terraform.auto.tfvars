# -----------------------------------------------------------------------------
# Secrets 
# -----------------------------------------------------------------------------

# Standarne sa nacitavaju z Terraform cloud

# Azure account subscription ID (Required)
subscription_id = "54f6edfe-b3a6-46ee-84bf-dc9a72bf1714"
# subscription_id = "d3c69ca6-2cb1-46d7-ba08-092dbd46a3bc"
tenant_id = "3590242b-a92d-4bb9-9ff9-eb7a1183f511"

# -----------------------------------------------------------------------------
# Global
# -----------------------------------------------------------------------------

location = "northeurope"

environment = "lab"

resource_group = "fsa_honcharenko"

# -----------------------------------------------------------------------------
# Resource Specific Variables
# -----------------------------------------------------------------------------

resource_name = "fsahoncharenko"

registry_name = "registry"
