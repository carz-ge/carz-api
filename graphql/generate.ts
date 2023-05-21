import {exec} from "child_process";

// run cmd command
const BASE_DIR = __dirname;
const SCHEMA_BLOB = `../src/main/resources/graphql/*.graphqls`;
const GENERATED_SCHEMA = `${BASE_DIR}/generated/schema.graphql`;

function execCallback(successText: string, reject: (reason?: any) => void, resolve: (value: (PromiseLike<unknown> | unknown)) => void) {
	return (error: any, stdout: any, stderr: any) => {
		if (error) {
			console.log(`error: ${error.message}`);
			reject(error);
			throw error;
		}
		if (stderr) {
			// TODO FIX - `ExperimentalWarning: stream/web is an experimental feature. This feature could change at any time`
			console.warn(`stderr: ${stderr}`);
		}
		console.log(successText);
		resolve(null);
	};
}

function execCommandAsync(command: string, successText: string) {
	return new Promise((resolve, reject) => {
		exec(command,
			execCallback(successText, reject, resolve));
	});
}

async function mergeSchema() {
	// -d does not work
	return execCommandAsync(`graphql-schema-utilities --schema ${SCHEMA_BLOB} --output ${GENERATED_SCHEMA}`,
		"Merge schema success");
}

function removeGeneratedFolder() {
	return execCommandAsync(`rm -rf ${BASE_DIR}/generated`, "Remove generated folder success");
}

console.log("-------------------GENERATION--START---------------------------")
removeGeneratedFolder()
	.then(mergeSchema)
	.catch((err: any) => {
		console.log("Caught error:",err);
		throw err;
	}).finally(() => {
		console.log("-------------------GENERATION--DONE---------------------------")
	});

