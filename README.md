# curl test commands (HW7)
#### get, success
`curl -s "http://localhost:8080/rest/meals/100002" |json_pp`

#### get between, success
`curl -s "http://localhost:8080/rest/meals/between?to=2015-05-31T12:00:00" |json_pp`

#### create, success (response headers are dumped to file called 'headers')
`curl -s -D headers -X POST -d '{"dateTime":"2015-06-01T18:00:00","description":"Created lunch","calories":300}' -H "Content-Type:application/json;charset=UTF-8" "http://localhost:8080/rest/meals" |json_pp`

#### get, not found (response headers are dumped to file called 'headers')
`curl -s -D headers "http://localhost:8080/rest/meals/100008"`

#### update, success
`curl -s -H 'Content-Type: application/json' -X PUT -d '{"dateTime":"2015-05-30T10:00", "description":"Updated breakfast", "calories":200}' http://localhost:8080/rest/meals/100002`

#### delete, success
`curl -s -X DELETE "http://localhost:8080/rest/meals/100002"`