class Cron {
  int id;
  String name;
  String email;
  String cron;
  String title;
  String text;
  String deviceToken;

  Cron({ this.id, this.name, this.email, this.cron, this.title, this.text, this.deviceToken });

  static Cron fromJson(Map map) {
    return Cron(
      id: map['id'],
      name: map['name'],
      email: map['userEmail'],
      cron: map['cronString'],
      title: map['pushTitle'],
      text: map['pushText'],
      deviceToken: map['userDeviceToken'],
    );
  }

  Map<String, String> toMap() {
    return {
      'name': name,
      'userEmail': email,
      'cronString': cron,
      'pushTitle': title,
      'pushText': text,
      'userDeviceToken': deviceToken,
    };
  }
}