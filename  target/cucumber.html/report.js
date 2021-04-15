$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("UpdateDB.feature");
formatter.feature({
  "line": 2,
  "name": "Update Hubspot",
  "description": "",
  "id": "update-hubspot",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@run1"
    }
  ]
});
formatter.scenario({
  "line": 4,
  "name": "Update Facebook Member data",
  "description": "",
  "id": "update-hubspot;update-facebook-member-data",
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
  "name": "user navigates to Member",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "Data is updated",
  "keyword": "Then "
});
formatter.step({
  "line": 8,
  "name": "DB is updated",
  "keyword": "Then "
});
formatter.match({
  "location": "Facebookupdate.navigateToFBandLogin()"
});
formatter.result({
  "duration": 23063362754,
  "status": "passed"
});
formatter.match({
  "location": "Facebookupdate.userNavigatesToMember()"
});
formatter.result({
  "duration": 16951341865,
  "status": "passed"
});
formatter.match({
  "location": "Facebookupdate.data_is_updated()"
});
formatter.result({
  "duration": 27459090,
  "status": "passed"
});
formatter.match({
  "location": "Facebookupdate.dbIsUpdated()"
});
formatter.result({
  "duration": 35416760011,
  "status": "passed"
});
});