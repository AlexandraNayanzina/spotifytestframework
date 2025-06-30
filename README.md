# spotifytestframework
API Automated Test Framework base on Spotify API  with Rest Assured, Udemy course

## RUn locally
```shell

# export variables
export base_uri=https://api.spotify.com
export account_base_uri=https://accounts.spotify.com
export client_id=<client id>
export client_secret=<client secret>
export refresh_token=<refresh token>
export user_id=<user id>

mvn clean test  \
    -DBASE_URI=$base_uri \
    -DACCOUNT_BASE_URI=$account_base_uri \
    -Dclient_id=$client_id \
    -Dclient_secret=$client_secret \
    -Dgrant_type=refresh_token \
    -Drefresh_token=$refresh_token \
    -Duser_id=$user_id
```