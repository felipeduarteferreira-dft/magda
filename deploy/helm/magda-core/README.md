# magda-core

![Version: 1.0.0-alpha.0](https://img.shields.io/badge/Version-1.0.0--alpha.0-informational?style=flat-square)

A complete solution for managing, publishing and discovering government data, private and open. This chart includes all core magda modules.

**Homepage:** <https://github.com/magda-io/magda>

## Source Code

* <https://github.com/magda-io/magda>

## Requirements

Kubernetes: `>= 1.14.0-0`

| Repository | Name | Version |
|------------|------|---------|
| file://../internal-charts/admin-api | admin-api | 1.0.0-alpha.0 |
| file://../internal-charts/apidocs-server | apidocs-server | 1.0.0-alpha.0 |
| file://../internal-charts/authorization-api | authorization-api | 1.0.0-alpha.0 |
| file://../internal-charts/authorization-db | authorization-db | 1.0.0-alpha.0 |
| file://../internal-charts/cloud-sql-proxy | cloud-sql-proxy | 1.0.0-alpha.0 |
| file://../internal-charts/combined-db | combined-db | 1.0.0-alpha.0 |
| file://../internal-charts/content-api | content-api | 1.0.0-alpha.0 |
| file://../internal-charts/content-db | content-db | 1.0.0-alpha.0 |
| file://../internal-charts/correspondence-api | correspondence-api | 1.0.0-alpha.0 |
| file://../internal-charts/elasticsearch | elasticsearch | 1.0.0-alpha.0 |
| file://../internal-charts/gateway | gateway | 1.0.0-alpha.0 |
| file://../internal-charts/indexer | indexer | 1.0.0-alpha.0 |
| file://../internal-charts/ingress | ingress | 1.0.0-alpha.0 |
| file://../internal-charts/opa | opa | 1.0.0-alpha.0 |
| file://../internal-charts/priorities | priorities | 1.0.0-alpha.0 |
| file://../internal-charts/rds-dev-proxy | rds-dev-proxy | 1.0.0-alpha.0 |
| file://../internal-charts/registry-api | registry-api | 1.0.0-alpha.0 |
| file://../internal-charts/registry-db | registry-db | 1.0.0-alpha.0 |
| file://../internal-charts/search-api-node | search-api-node | 1.0.0-alpha.0 |
| file://../internal-charts/search-api | search-api | 1.0.0-alpha.0 |
| file://../internal-charts/session-db | session-db | 1.0.0-alpha.0 |
| file://../internal-charts/storage-api | storage-api | 1.0.0-alpha.0 |
| file://../internal-charts/tenant-api | tenant-api | 1.0.0-alpha.0 |
| file://../internal-charts/tenant-db | tenant-db | 1.0.0-alpha.0 |
| file://../internal-charts/web-server | web-server | 1.0.0-alpha.0 |
| https://charts.magda.io | preview-map(magda-preview-map) | 1.0.1 |

## Values

| Key | Type | Default | Description |
|-----|------|---------|-------------|
| global.authPluginRedirectUrl | string | `"/sign-in-redirect"` | the redirection url after the whole authentication process is completed. Authentication Plugins will use this value as default setting. The following query paramaters can be used to supply the authentication result: <ul> <li>result: (string) Compulsory. Possible value: "success" or "failure". </li> <li>errorMessage: (string) Optional. Text message to provide more information on the error to the user. </li> </ul> The default "/sign-in-redirect" url supports an additional `redirectTo` query parameter. If this parameter not presents, user will be redirected further (at frontend) to account page /account. Otherwise, user will redirected to the url sepcified by `redirectTo` query parameter. |
| global.awsRdsEndpoint | string | `nil` | AWS RDS DB instance access endpoint. e.g. xxxx.xxxx.ap-southeast-2.rds.amazonaws.com. Compulsory if `useAwsRdsDb` = true |
| global.dbMasterUsername | string | `"postgres"` | Database master username.  Although this config allows to set a different DB username, use a DB username rather than `postgres` will  trigger an error when migrate content DB until this ticket #3126 is fixed. master user password will be loaded from "cloudsql-db-credentials" secret `password` field (even for AWS RDS for backward compatibility) |
| global.defaultAdminUserId | string | `"00000000-0000-4000-8000-000000000000"` |  |
| global.enableMultiTenants | bool | `false` |  |
| global.enablePriorityClass | bool | `false` |  |
| global.exposeNodePorts | bool | `false` |  |
| global.externalUrl | string | `"http://localhost:6100"` |  |
| global.gapiIds | list | `[]` |  |
| global.image.pullPolicy | string | `"IfNotPresent"` |  |
| global.image.repository | string | `"data61"` |  |
| global.logLevel | string | `"INFO"` |  |
| global.namespace | string | `"default"` |  |
| global.noDbAuth | bool | `false` |  |
| global.rollingUpdate.maxUnavailable | int | `0` |  |
| global.useAwsRdsDb | bool | `false` | whether to use AWS RDS DB config. When this option is on, `useCombinedDb` & `useCloudSql` must be turned off. |
| global.useCloudSql | bool | `false` |  |
| global.useCombinedDb | bool | `true` |  |
| tags | object | see default value of each individual tag below. | (object) Control on/ off of each modules.  To turn on/off openfaas, please set value to `global.openfaas.enabled` |
| tags.admin-api | bool | `false` | turn on / off [admin-api](../internal-charts/admin-api/README.md) |
| tags.all | bool | `true` | turn on / off all modules |
| tags.apidocs-server | bool | `false` | turn on / off [apidocs-server](../internal-charts/apidocs-server/README.md) |
| tags.authorization-api | bool | `false` | turn on / off [authorization-api](../internal-charts/authorization-api/README.md) |
| tags.authorization-db | bool | `false` | turn on / off [authorization-db](../internal-charts/authorization-db/README.md) |
| tags.combined-db | bool | `false` | turn on / off [combined-db](../internal-charts/combined-db/README.md) |
| tags.content-api | bool | `false` | turn on / off [content-api](../internal-charts/content-api/README.md) |
| tags.content-db | bool | `false` | turn on / off [content-db](../internal-charts/content-db/README.md) |
| tags.correspondence-api | bool | `false` | turn on / off [content-db](../internal-charts/correspondence-api/README.md) |
| tags.elasticsearch | bool | `false` | turn on / off [elasticsearch](../internal-charts/elasticsearch/README.md) |
| tags.gateway | bool | `false` | turn on / off [gateway](../internal-charts/gateway/README.md) |
| tags.indexer | bool | `false` | turn on / off [indexer](../internal-charts/indexer/README.md) |
| tags.ingress | bool | `false` | turn on / off [ingress](../internal-charts/ingress/README.md) |
| tags.opa | bool | `false` | turn on / off [opa](../internal-charts/opa/README.md) |
| tags.preview-map | bool | `false` | turn on / off [preview-map](https://github.com/magda-io/magda-preview-map) |
| tags.priorities | bool | `true` | whether or not deploy Magda defined PriorityClass. Useful to schedule different payload on different nodes. |
| tags.registry-db | bool | `false` | turn on / off [registry-db](../internal-charts/registry-db/README.md) |
| tags.search-api | bool | `false` | turn on / off [search-api](../internal-charts/search-api/README.md) |
| tags.search-api-node | bool | `false` | turn on / off [search-api-node](../internal-charts/search-api-node/README.md) |
| tags.session-db | bool | `false` | turn on / off [session-db](../internal-charts/session-db/README.md) |
| tags.storage-api | bool | `false` | turn on / off [storage-api](../internal-charts/storage-api/README.md) |
| tags.tenant-api | bool | `false` | turn on / off [tenant-api](../internal-charts/tenant-api/README.md) |
| tags.tenant-db | bool | `false` | turn on / off [tenant-db](../internal-charts/tenant-db/README.md) |
| tags.web-server | bool | `false` | turn on / off [web-server](../internal-charts/web-server/README.md) |

