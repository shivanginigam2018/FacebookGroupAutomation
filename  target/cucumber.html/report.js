$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Login.feature");
formatter.feature({
  "line": 2,
  "name": "Validate FB Group Request Update",
  "description": "",
  "id": "validate-fb-group-request-update",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@run"
    }
  ]
});
formatter.scenario({
  "line": 4,
  "name": "Update Facebook Member data",
  "description": "",
  "id": "validate-fb-group-request-update;update-facebook-member-data",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "user login to facebook",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "user navigates to Member requests and collate data",
  "keyword": "When "
});
formatter.match({
  "location": "Facebookupdate.navigateToFBandLogin()"
});
