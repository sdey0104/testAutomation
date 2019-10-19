##process to run some smoke test of YellowSoap rest endpoints.

There are two processes you can do this.

(Please note that, the workflow is shared among everybody, please don’t change it, you can copy the environment and put it into your own environment and play with it)

#Process A : Postman App

- Install postman app from here and create an account with your yellow brick email address. 
- Accept the invite (if you have not received the mail, contact with me) to add you. You will be able to see the Yellowbrick team in Postman workspace.
yellowsoap_prod env should be added as your environment (on the right side of the window)
- YellowSoap Prod Rest Endpoint collection should be added automatically as well.
- Click on the YellowSoap Prod Rest - collection arrow and then run, put Delay ( consecutive api request interval delay) to 1000 ms and then run YellowSoap. You should be able to see the results.


#Process B : Postman App (If you want to run it from shell or similar environment)

- Install updated version of Newman from here
- Make sure you have the config file and collection file(I created a git commit and kept those under yellow-sources/resources/test_automation/rest_api directory, having problem with git, so couldn’t push, will push it soon)
- Run the following command, replace your path of the config file depending your Yellow Source clone directory,
```sh
./test_rest_api.sh
```

Enjoy !! 