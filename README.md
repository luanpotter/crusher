# crusher

Crusher is a mobile app that allows you to shedule push notifcations for yourself using a cron string.

Notifications are fired every full hour, so you cannot schedule more specific than that.

The cron string therefore is truncated from the beggining to match that fact:

```
 _________ - hour of day (0-23)
 | _______ - day of month (1-31)
 | | _____ - month (1-12)
 | | | ___ - day of week (0-6), (0 = Sun, 1 = Mon, 6 = Sat)
 | | | |
 * * * *
```

The app is made with Flutter and the backend uses Kotlin with Spark deployed on Heroku, Postgres database provided by Heroku.

Login is done via Firebase with Google Sign In.

Push Notifications are delivered via Firebase's FCM, and there is no guarantee (though it's pretty safe).

Times **must** be specified in UTC.

## Example Crons

* `10 * * *` -> every day at 10am
* `* * * *` -> every hour of every day
* `17 1 * *` -> every first day of month, at 17:00 (5pm)
* `7 * * 1` -> every monday at 10:00 (7am)

You can also use ranges (`-`), lists, and any other standard cron consstruct. Please check [wikipedia](https://en.wikipedia.org/wiki/Cron) for more details on cron strings.
