import 'package:flutter/material.dart';

import '../api/cron_api.dart';
import '../api/api_exception.dart';
import '../login.dart';
import '../model/cron.dart';
import '../util.dart';
import '../widget/cron_line.dart';

class MainPage extends StatefulWidget {
  final Login user;
  final void Function() logout;

  const MainPage({Key key, @required this.user, @required this.logout})
      : super(key: key);

  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  bool loading = true;
  String errorMessage;
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
    try {
      List<Cron> crons = await CronApi.fetchCrons(widget.user);
      this.setState(() {
        this.loading = false;
        this.crons = crons;
        this.errorMessage = null;
      });
    } on ApiException catch (ex) {
      print('Error: $ex');
      this.setState(() {
        this.loading = false;
        this.errorMessage =
            'Error fetching crons: ${ex.message}; please tap to retry';
      });
    }
  }

  void retryFetch() {
    this.setState(() {
      this.loading = true;
      this.errorMessage = null;
    });
    this.fetchCrons();
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
          builder: (_) {
            return AlertDialog(
              title: Text('Create new Cron'),
              content: loader(
                  loading,
                  SingleChildScrollView(
                    child: Column(
                      children: [
                        input(_nameController, 'Name',
                            'Name/desc of this cron (just for ref.)'),
                        input(_cronController, 'Cron',
                            'Cron string for this cron (start with the hour)'),
                        input(_titleController, 'Title',
                            'Title for the push notf'),
                        input(
                            _textController, 'Text', 'Text for the push notf'),
                      ],
                    ),
                  )),
              actions: [
                loader(
                    loading,
                    RaisedButton(
                      child: Text('Cancel'),
                      onPressed: () => Navigator.of(context).pop(),
                    )),
                loader(
                    loading,
                    RaisedButton(
                      child: Text('Create'),
                      onPressed: () => this.createCron(context),
                    )),
              ],
            );
          },
        );
      },
    );
  }

  void createCron(context) async {
    this.setState(() => this.loading = true);
    try {
      String deviceToken = await widget.user.getDeviceToken();
      print("Using device token: $deviceToken");
      await CronApi.createCron(
        widget.user,
        Cron(
          name: _nameController.text,
          email: widget.user.email,
          cron: _cronController.text,
          title: _titleController.text,
          text: _textController.text,
          deviceToken: deviceToken,
        ),
      );
      Navigator.of(context).pop();
      this.fetchCrons();
    } on ApiException catch (ex) {
      print('Error: $ex');
      toast(context, 'Error creating your cron: ${ex.message}');
      this.setState(() => this.loading = false);
    }
  }

  void remove(Cron cron) async {
    this.setState(() => this.loading = true);
    try {
      await CronApi.deleteCron(widget.user, cron);
      Navigator.of(context).pop();
      this.fetchCrons();
    } on ApiException catch (ex) {
      print('Error: $ex');
      toast(context, 'Error creating your cron: ${ex.message}');
      this.setState(() => this.loading = false);
    }
  }

  Widget buildContent() {
    if (loading) return Center(child: CircularProgressIndicator());
    if (errorMessage != null)
      return GestureDetector(
          child: Center(child: Text(errorMessage)), onTap: this.retryFetch);
    return Column(
      children: this
          .crons
          .map((c) => CronLine(cron: c, remove: this.remove))
          .toList(),
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
