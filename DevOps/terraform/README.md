# FSA Terraform

- [Resource Group](./01-resource-group/) v Azure Cloude.
- [ACR - Azure Container registry](./02-container-registry/), ktoré slúži na ukladanie výsledných Docker image-ov.
  - Taktiež vytvára `Container Instance`, ktorá spúšťa dopredu pripravený [Jenkins docker image](#TODO pridat Github link).
- [AKS - Azure Kubernetes Services](./03-kubernetes/) - slúži ako prostredie, kde sú spustené výsledné Aplikácie vytvorené vrámci FSA.
- [Azure Public IP](./04-public-ip/) - Slúži na vypublikovanie UI výsledného projektu do internetu.
- [Azure Flexible PostgreSQL](./05-psql/) - Slúži na vytvorenie databázy, na ktorú sa aplikácie pripoja a budú si ukladať dáta.

---

- [Common ACR](./00-fsa-common/) - Tento register je vytvorený pre všetkých účastníkov FSA, slúži na vytvorenie Public DNS zóny a Resource group, v ktorej je public DNS zóna vytvorená a taktiež na vytvorenie Enterprise Application a Service Principal, pomocou ktorého sa autentifikujeme do Giltabu cez Azure Entra ID.

---

## Štruktúra jednotlivých priečinkov

- Všeobecné súbory:
  - [.env](./00-fsa-common/.env) - slúži ako pomocník pri spúštaní Terraform kódu, aby sme nemali citlivé údaje commitnuté v Git-e.
  - [.gitignore](./00-fsa-common/.gitignore) - aby Git ignoroval pri commitovaní určitý typ súborov - podľa typu súboru alebo názvu.
- Terraform súbory:
  - [data.tf](./03-kubernetes/data.tf) - Slúži na získanie informácií o vytvorenom resource, ktorý sme nevytvaráli v danom Terraform kóde (v aktuálnom priečinku) alebo nie je vytvorený pomocou Terraform-u vôbec.
  - [main.tf](./00-fsa-common/main.tf) - Nachadza sa tu kod, ktory vytvara konkretne resources.
  - [output.tf](./03-kubernetes/output.tf) - Sluzi ako definícia, toho, čo má Terraform uložiť, ako výstup po vytvorení resource-u.
  - [versions.tf](./00-fsa-common/versions.tf) - Definícia verzie `Terraform`, `Terraform provider-a` a úložisko `Terraform Statefile`.
  - [variables.tf](./00-fsa-common/variables.tf) - Definícia `Terraform premennych`. Môže obsahovať krátky popis ako aj východziu hodnotu premennej.
  - [terraform.auto.tfvars](./00-fsa-common/terraform.auto.tfvars) - Obsahuje naplnenie premenných reálnymi dátami, ktoré chceme používať.


## Spušťanie kódu

```sh
# Inicializacia Terraform-u - stiahnutie Provider-ov, vytvorenie .tfstate (staci pustit iba raz).
terraform init

# Plan - ukaze vykonavane zmeny, ale NEVYKONA ich.
terraform plan

# Apply - Ukaze vykonavane zmeny a po potvrdeni aplikuje.
terraform apply
```
