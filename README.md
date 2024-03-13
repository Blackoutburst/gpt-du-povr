# GTP du pauvre

## Setup

Create a file called `local.properties` based on the example file `local.properties.example` at the root of the project\
Fill it with the discord bot token and the OpenAI api key
```gradle
discordToken=
openaiKey=
```

Run the command
```bash
./gradlew generateBuildConfig 
```

GG WP you should now be able to run the project
