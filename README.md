## Running Api

**Spinning up the Database locally**

> first install `docker` and `docker-compose`
> then from root directory run `docker-compose up -d db`

**Run the application**
> add this to your Environment Variables in intelliJ: AWS_ACCESS_KEY_ID={AWS_ACCESS_KEY_ID};AWS_SECRET_ACCESS_KEY={AWS_SECRET_ACCESS_KEY};AWS_REGION={region}. Use your aws security creds.
> In IntelliJ override the ENVIRONMENT variable by adding `-DENVIRONMENT` to the `VM options` in the run configuration.
> Once you press play and recieve success message in the console, the API is ready to receive request.