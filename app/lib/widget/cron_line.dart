import 'package:flutter/material.dart';

import '../model/cron.dart';
import '../util.dart';

class CronLine extends StatelessWidget {
  final Cron cron;
  final void Function(Cron) remove;

  const CronLine({Key key, @required this.cron, @required this.remove}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return pad(
      Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(cron.name),
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              Text(cron.cron),
              IconButton(
                icon: Icon(Icons.delete),
                onPressed: () => this.remove(cron),
              )
            ],
          )
        ],
      ),
    );
  }
}
