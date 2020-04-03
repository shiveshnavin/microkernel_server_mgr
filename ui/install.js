const { exec } = require("child_process");
var op = function (error, stdout, stderr) {
    if (error) {
        console.log(`ERR : ${error.message}`);
        return;
    }
    if (stderr) {
        console.log(`WARN: ${stderr}`);

    }
   
    console.log(`INFO: ${stdout}`);
    step = step + 1
    if (step < commands.length)
        doit(step);
};

 
var step = 0
var commands = [ 
{
    msg: ">> Installing Setup Dependencies...This may take a while", cmd: function () {
        console.log("\x1b[0m","Starting...")
        try{
            var fs=require.resolve("fs")
            exec("echo Dependencies Installed", op);
        }catch(e)
        {
            exec("npm install --save-dev fs --cache-min 999999999", op);
        }
    }
},{
    msg: ">> Check Install", cmd: function (stepp) {
        var fs = require("fs")
        if (fs.existsSync('.localui5')) {
            console.log("\x1b[32m","LocalUI5 Already Installed : Delete .localui5 and rerun index.js to ReInstall")
            step=commands.length - 1
            doit(step)
            
        }
        else{

            step=step+1
            doit(step)
        }
    }
},{
    msg: ">> Adding @sap to NPM registry", cmd: function () {

        console.log("\x1b[42m",`This will modify/create package.json and ui5.yaml (Will create backups first) . 
        It is advised to add these files to GITIGNORE in case you wish to deploy the app on Cloud . For more info contact @shiveshnavin .
        Make sure to use src="https://sapui5.hana.ondemand.com/1.69.0/resources/sap-ui-core.js" in your index.html or install the UI5 SDK in /resoruces . PRESS ANY KEY TO CONTINUE...`);
       
        console.log('Press any key to continue or CTRL+C to cancel.');
        process.stdin.once('data', function () {
        
            console.log( "\x1b[0m","Continuing...")
            exec("npm config set @sap:registry https://npm.sap.com", op);

        });
           
            
    }
}, {
    msg: ">> Installing UI5 Tooling", cmd: function () {

        exec("npm list --global", function (error, stdout, stderr) {
            if (error) {
          //      console.log(`ERR : ${error.message}`);
               
            }
            if (stderr) {
           //     console.log(`WARN: ${stderr}`);

            }
          //  console.log(`INFO: ${stdout}`);
            if (stdout.indexOf("@ui5/cli") > -1) {
                console.log("\x1b[33m","UI5 Found. Skipping install");
                exec("echo SKIP", op)
            }
            else {
                console.error("UI5 is not found. Installing...This may take a while");
                exec("npm install --global @ui5/cli  --cache-min 999999999", op)
            }
        })


    }
}, {
    msg: ">> Initialize local ui5 .", cmd: function () {

        var fs = require("fs")
        if (fs.existsSync('ui5.yaml')) {

		if (!fs.existsSync('ui5.yaml.bak')) {
			 fs.copyFileSync("ui5.yaml","ui5.yaml.bak")
		}
       
           
            var conf=fs.readFileSync('ui5.yaml')
            console.log("\x1b[31m",`ALERT !! Looks like you have ui5.yaml present . We will try to add dependency at the end of file .
             If error occurs please remove ui5.yaml and replace it with the backup file ui5.yaml.bak by renaming`)
             console.log("And Add the below configuration to ui5.yaml . You may want to remove the custom tasks if build fails")
             
            
 var text=`
 
server:
  customMiddleware:
    - name: ui5-middleware-cfdestination
      afterMiddleware: compression
      configuration:
         debug: true
         port: 9087
         xsappJson: "xs-app.json"
         destinations:
            # check that the destination name (here: "api") matches your router in xssppJson 
            - name: "api"
              url: "http://services.odata.org"`
              console.log("\x1b[32m",text)
              console.log("\n\n\n")
            if(conf.indexOf("ui5-middleware-cfdestination")<=-1)
            {
                fs.appendFileSync('ui5.yaml', text);
            }           

        }
        else {
var text=`specVersion: '1.0'
metadata:
 name: ui
type: application
server:
  customMiddleware:
    - name: ui5-middleware-cfdestination
      afterMiddleware: compression
      configuration:
         debug: true
         port: 9087
         xsappJson: "xs-app.json"
         destinations:
            # check that the destination name (here: "api") matches your router in xssppJson 
            - name: "api"
              url: "http://services.odata.org"`
           
            fs.writeFileSync('ui5.yaml', text);
        }
        console.log("\x1b[32m","~~~~~~~~Configure your destinations in ui5.yaml~~~~~~~~~")    
            console.log("\x1b[0m","... ")

        step = step + 1
        if (step < commands.length)
            doit(step);


    }
}, {
    msg: ">> Installing dependency for Destinations...This may take a while", cmd: function () {
        try{

          
            exec("npm list --local", function (error, stdout, stderr) {
                if (error) {
                //    console.log(`ERR : ${error.message}`);
                    
                }
                if (stderr) {
                 //   console.log(`WARN: ${stderr}`);
    
                } 
                if (stdout.indexOf("ui5-middleware-cfdestination") > -1 || stderr.indexOf("ui5-middleware-cfdestination") > -1 ) {
                    console.log("ui5-middleware-cfdestination found already... skipping")
                    exec("echo SKIP", op)
                }
                else {
                    console.log("\x1b[33m","If this step fails. Download and extract https://github.com/petermuessig/ui5-ecosystem-showcase/archive/master.zip and run the below command in current directory.")
                    console.log("\x1b[33m",`npm install --save-dev <path to extracted folder>\\ui5-ecosystem-showcase-master\\packages\\ui5-middleware-cfdestination`)
                    console.log( "\x1b[0m","Trying to install destination middleware...")
                    exec("npm install --save-dev ui5-middleware-cfdestination  --cache-min 999999999", op);
                }
            })

            

        }catch(e)
        {

        }
        

    }
}, {
    msg: ">> Configuring Destination Server", cmd: function () {
        var fs = require("fs")
		if (!fs.existsSync('package.json.bak')) {
			 fs.copyFileSync("package.json","package.json.bak")
		}
       
        var package=fs.readFileSync("package.json")
        var pkg=JSON.parse(package)
        pkg.ui5 = {dependencies:["ui5-middleware-cfdestination"]}
        console.log(pkg)
        fs.writeFileSync("package.json",JSON.stringify(pkg, null, 4))
        step = step + 1
        if (step < commands.length)
            doit(step);
    }
}, {
    msg: ">> Building local ui5", cmd: function () {
        console.log("\x1b[31m","If build fails remove ui5.yaml and run install.js again . MAKE SURE TO ADD UI5.YAML TO GITIGNORE for local uses")
       console.log( "\x1b[0m","Building.....")
       

        exec("ui5 build", op);
    }
}, {
    msg: ">> Saving config", cmd: function () {
        var fs=require("fs")
        var text=`{
            "welcomeFile": "index.html",
            "authenticationMethod": "none",
            "routes": [{
                  "source": "/api/(.*)",
                  "target": "$1",
                  "destination": "api"
              },
              {
                "source": "/",
                "target": "/",
                "localDir": "dist"
              }
            ]
          }
          `;
          if (!fs.existsSync('xs-app.json')) {
              
          fs.writeFileSync("xs-app.json",text);

          }

        exec("echo 'done' >> .localui5", op);
    }
}, {
    msg: ">> Installed.. Run and goto http://127.0.0.1:<PORT>/index.html or your configured URL", cmd: function () {
       console.log("Make sure to add destination in ui5.yaml (restart required)")
       console.log("\x1b[33m","To start execute "+(process.platform=='win32'?'run.bat':'run.sh'))
       console.log("\x1b[0m","You can close this window now .")
       var text ;
       var file;
       text = 
`@echo off
cmd /c ui5 build
cmd /c ui5 serve
echo Exiting...
pause
`

        if(process.platform === 'win32')
        {
           file = "run.bat"
        }
        else
        {
            file = "run.sh"
            text = `ui5 build 
ui5 serve`
        }

        var fs = require("fs") 
        fs.writeFileSync(file,text)
    }
}]
function doit(step) {
    var element = commands[step]
    console.log(element.msg)

    element.cmd(step)
}
doit(step)





