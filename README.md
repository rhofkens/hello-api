# Goal of this repo
I wanted to create a backend with high-quality code using aider.chat, GPT 4o, o1 and sonnet.
All the code in this repo was created by prompting, I did not write a single line of code :-).
The web UI that calls this backend was prompted in GPT engineer.
We live in magic times!

# hello-api
Backend for the "hello people" application
The backend contains two endpoints:
1. /people : CRUD methods to create, list, update and delete persons
2. /people/welcome : GET method to create a welcome message for all people
The people entities are persisted in an H2 memory database.
