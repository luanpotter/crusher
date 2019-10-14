class Cron {
  String name;

  Cron({ this.name });

  static Cron fromJson(Map map) {
    return Cron(
      name: map['name'],
    );
  }
}