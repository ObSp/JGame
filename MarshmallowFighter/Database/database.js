const TOKEN = "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"
const GIST_ID = "d61df94a1b7440581d7db8b403212928";
const DATABASE_NAME = "db.json"
const FETCH_URL = `https://api.github.com/gists/${GIST_ID}`

/** Sets the given key in the database to equal the given data
 * 
 * @param {*} key 
 * @param {*} data 
 */
async function set(key, data){
    const curdata = await rawget()
    curdata[key] = data;
    rawset(curdata);
}


async function rawset(data){
    const req = await fetch(FETCH_URL, {
        method: "PATCH",
        headers: {
            Authorization: `Bearer ${TOKEN}`
        },
        body: JSON.stringify({
            files: {
                [DATABASE_NAME]: {
                    content: JSON.stringify(data)
                }
            }
        })
    })
    return req.json()
}

/**Returns the value of the given key in the database
 * 
 * @param {*} key 
 */
async function get(key){
    const data = await rawget();
    const val = data[key]
    return val
}


/**Returns the raw data of the JSON file
 * 
 */
async function rawget(){
    const req = await fetch(FETCH_URL);
    const gist = await req.json();
    return JSON.parse(gist.files[DATABASE_NAME].content);
}

/**Clears all data in the database
 * 
 */
async function clear(){
    rawset({})
}

async function removeKey(key){
    const curdata = await rawget();
    const newdata = {};
    Object.keys(curdata).forEach(k => {
        if (k !== key) newdata[k] = curdata[k]
    })
    rawset(newdata)
}