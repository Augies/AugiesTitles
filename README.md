# AugiesTitles
AugiesTitles is a plugin aimed at making title management easy on servers. It is originally written for use with LuckPerms. AugiesTitles gives admins the ability to create any number of custom titles that players can select for use. There is also a system for players to create their own titles!
##Configuration
All configuration files for the plugin are in json, stored in the folder `AugiesTitles`
###Storage Configuration (`databaseConfig.json`)
By default, everything is stored in the `data` folder of the plugin. If you want to hook into a database, there is MySQL support.
To enable MySQL support, simply change `storageType` from `LOCAL` to `MYSQL`
The following currently-supported storage types are as follows (case-sensitive): LOCAL, MYSQL