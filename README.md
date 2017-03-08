# BlockChain
Generic Open Source Block Chain

[![Build Status](https://travis-ci.org/himanshuo/BlockChain.svg?branch=master)](https://travis-ci.org/himanshuo/BlockChain)


# Setup Instructions

1. This Repository uses Maven as a build management tool. Install it at http://maven.apache.org/
2. To build the code

    mvn package

3. To Run the code

    java -jar target/BlockChain-1.0-SNAPSHOT.jar




# Requirements

# TODO
* Use a logger instead of println
* Genesis Block
* update Coinbase Transaction
* Use original paper (https://bitcoin.org/bitcoin.pdf) and online article (http://www.michaelnielsen.org/ddi/how-the-bitcoin-protocol-actually-works/) to determine requirements
* update java version that travis uses
* dockerize
* Internet.java should be replaced with a real internet
* A more normal interface to the Internet for broadcasting messages like this should be adopted. Some custom protocol built atop TCP and/or Thrift?
* http://escoffier.me/vertx-hol/
* store hash instead of complete transaction in ledgers
* Erlang - https://github.com/pinterest/elixir-thrift
* Thrift
* GUI client
* config file for custom version of blockchain
* config database for custom version of blockchain?
* Smart Contracts
* Rename BlockChain -> MyBlockChain?
* SideChain
