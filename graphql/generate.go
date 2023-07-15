package main

import gql "github.com/mattdamon108/gqlmerge/lib"
import "fmt"

func main(){
	// ...

	// "  " is indent for the padding in generating schema
	// in case of using as go module, just " " would be fine
	//
	// paths should be a relative path

	//path := "../src/main/resources/graphql/auth.graphqls";
	schema := gql.Merge(" ", "../src/main/resources/graphql/auth.graphqls", "../src/main/resources/graphql/root.graphqls")
	fmt.Println(schema)
}
