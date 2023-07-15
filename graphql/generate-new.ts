
import * as fs from "fs";
const { GraphQLFileLoader } = require("@graphql-tools/graphql-file-loader");
const { loadSchemaSync } = require("@graphql-tools/load");
const { printSchemaWithDirectives } = require("@graphql-tools/utils");

const SCHEMA_BLOB = `../src/main/resources/graphql/*.graphqls`;
const schema = loadSchemaSync(SCHEMA_BLOB, {
    loaders: [new GraphQLFileLoader()],
});

if (!fs.existsSync("generated"))  {
    fs.mkdirSync("generated")
}

fs.writeFileSync("generated/schema.graphql", printSchemaWithDirectives(schema));

//
// import * as path from "path";
//
// import {loadFilesSync} from "@graphql-tools/load-files";
//
// import {mergeTypeDefs, mergeResolvers} from "@graphql-tools/merge";
// import { makeExecutableSchema } from '@graphql-tools/schema'
// //
// // const typesArray = loadFilesSync(path.join(__dirname, SCHEMA_BLOB))
// // let typeDefs = mergeTypeDefs(typesArray);
// // // let resolvers = mergeResolvers(typesArray);
// //
// // // console.log(JSON.stringify(typeDefs, null, 2));
// //
// // const jsSchema = makeExecutableSchema({
// //     typeDefs,
// //     resolverValidationOptions: {}, // optional
// //     inheritResolversFromInterfaces: false // optional
// // })
// //
// // console.log(JSON.stringify(jsSchema, null, 2));
// //
// //
// // // fs.writeFile("tt", JSON.stringify(jsSchema, null, 2))
