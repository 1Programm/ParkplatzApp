# Frontend Service

## 1) Install go
https://go.dev/dl/

## 2) Download the OAuth2 Proxy App
go install github.com/oauth2-proxy/oauth2-proxy/v7@latest  

(Or manually download the binary from:)
https://github.com/oauth2-proxy/oauth2-proxy/releases

## 3) Find the installed binary

Try 'go env' to see environment variables and find the 'GOPATH' variable.

There should be the binary installed '$GOPATH/oauth2-proxy'

## 4) Run the binary with the config file in this directory
(Assuming we are in the folder where this config file is)

### Running with:
$GOPATH/oauth2-proxy --config=proxy-config.properties


