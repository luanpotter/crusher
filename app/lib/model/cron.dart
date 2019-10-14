class Cron {
  String name;
  String email;
  String cron;
  String title;
  String text;

  Cron({ this.name, this.email, this.cron, this.title, this.text });

  static Cron fromJson(Map map) {
    return Cron(
      name: map['name'],
      email: map['userEmail'],
      cron: map['cronString'],
      title: map['title'],
      text: map['text'],
    );
  }

  Map<String, String> toMap() {
    return {
      'name': name,
      'userEmail': email,
      'cronString': cron,
      'title': title,
      'text': text,
    };
  }
}