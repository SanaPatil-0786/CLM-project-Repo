Feature: verify contract header infomation api of CLM project

Scenario: verify GetTokenAPI and take generated token
Given  add request payload for "GetAuthToken"
When  post "GetAuthToken" http API request
Then take "tokenvalue" from responce payload for "GetAuthToken"
And  check response status code is "200"

Scenario: verify SaveHeaderInformation and take data key value
Given add request payload for "SaveHeaderInformation"
When  post "SaveHeaderInformation" http API request
Then take "data" from responce payload for "GetAuthToken"
And  check response status code is "200"

Scenario: verify GetHeaderInformation API is contain same details of post headerinformaiton API
Given  add request payload for "GetContractHeaderDetails"
When  Get "GetContractHeaderDetails" http API request
Then compare data from responce payload is contain same keys and values of post data
And  check response status code is "200"

