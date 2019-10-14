import 'package:flutter/material.dart';

import '../api.dart';
import '../login.dart';
import '../model/cron.dart';
import '../util.dart';

class MainPage extends StatefulWidget {
  final Login user;
  final void Function() logout;

  const MainPage({Key key, this.user, this.logout}) : super(key: key);

  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  bool loading = true;
  List<Cron> crons;

  TextEditingController _nameController = TextEditingController();
  TextEditingController _cronController = TextEditingController();
  TextEditingController _titleController = TextEditingController();
  TextEditingController _textController = TextEditingController();

  @override
  void initState() {
    super.initState();
    this.fetchCrons();
  }

  void fetchCrons() async {
    List<Cron> crons = await Api.fetchCrons(widget.user);
    this.setState(() {
      this.loading = false;
      this.crons = crons;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        pad(buildHeader()),
        Expanded(child: buildContent()),
        pad(buildFooter(context)),
      ],
    );
  }

  Widget buildFooter(BuildContext context) {
    return RaisedButton(
      child: Text('Add Cron'),
      onPressed: () {
        showDialog(
          context: context,
          builder: (context) {
            return AlertDialog(
              title: Text('Create new Cron'),
              content: SingleChildScrollView(
                child: Column(
                  children: [
                    input(_nameController, "Name",
                        "Name/desc of this cron (just for ref.)"),
                    input(_cronController, "Cron",
                        "Cron string for this cron (start with the hour)"),
                    input(_titleController, "Title", "Title for the push notf"),
                    input(_textController, "Text", "Text for the push notf"),
                  ],
                ),
              ),
              actions: [
                RaisedButton(
                  child: Text('Cancel'),
                  onPressed: () => Navigator.of(context).pop(),
                ),
                RaisedButton(
                  child: Text('Create'),
                  onPressed: this.createCron,
                ),
              ],
            );
          },
        );
      },
    );
  }

  void createCron() async {
    this.setState(() => this.loading = true);
    await Api.createCron(
        widget.user,
        Cron(
          name: _nameController.text,
          email: widget.user.email,
          cron: _cronController.text,
          title: _titleController.text,
          text: _textController.text,
        ));
    this.fetchCrons();
  }

  Widget buildContent() {
    if (loading) return Center(child: CircularProgressIndicator());
    return Column(
      children: this.crons.map((c) => Text('Cron: ${c.name}')).toList(),
    );
  }

  Widget buildHeader() {
    return Row(
      children: [
        Text('Logged in as ${widget.user.email}'),
        RaisedButton(
          child: Text('Sign out'),
          onPressed: widget.logout,
        ),
      ],
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
    );
  }
}
