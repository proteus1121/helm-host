# lambdaHelm
AWS lambda for parsing helm attributes from helm charts.
How to setup:
1.  mvn package
2. serverless deploy -v

Note: don't forget to setup serverless https://www.npmjs.com/package/serverless
and configure aws IAM.

How to use:
When you deploy serverless app, you will get a link to aws:
https://<AWS_LINK>.amazonaws.com/dev/simple

Use POST request with attached binary helm chart in the body and AssetKind (HELM_PACKAGE or HELM_PROVENANCE) as header.
You should get json with helm attributes:

```json
{
  "name": "distributed-jmeter",
  "version": "1.0.1"
}
 ```
