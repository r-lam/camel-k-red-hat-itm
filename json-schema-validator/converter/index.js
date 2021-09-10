// Imports
var toJsonSchema = require('@openapi-contrib/openapi-schema-to-json-schema');
var fs = require('fs');

var schema = require("./inventory.json");

var convertedSchema = toJsonSchema(schema);

fs.writeFile('inventory-schema.json', JSON.stringify(convertedSchema), err => {
    if (err) {
        console.error(err)
        return
    }
});

console.log(convertedSchema);