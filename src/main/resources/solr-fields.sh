curl -X POST -H 'Content-type:application/json' --data-binary '{
  "replace-field":{
     "name":"diseaseName",
     "type":"text_general",
     "stored":true }
}' http://localhost:8983/solr/gettingstarted/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "replace-field":{
     "name":"introduction",
     "type":"text_general",
     "stored":true }
}' http://localhost:8983/solr/gettingstarted/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "replace-field":{
     "name":"prevention_cure",
     "type":"text_general",
     "stored":true }
}' http://localhost:8983/solr/gettingstarted/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "replace-field":{
     "name":"symptom",
     "type":"text_general",
     "stored":true }
}' http://localhost:8983/solr/gettingstarted/schema

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "replace-field":{
     "name":"cause",
     "type":"text_general",
     "stored":true }
}' http://localhost:8983/solr/gettingstarted/schema