<?php

function sendPustNotification($to = '', $data = array())
{
    $apiKey = 'AAAAqIfgvbc:APA91bFi-uijDXZl6HsJScd5X0J3-iGDpFldz5otamSkicH8LOha6hhgbPRBlmcSfWB298yzGwrtxURXx6P1PTGW8pJ1L3qfdCzEOdxJCU0sqRB7B6U_kOFNOF7MFwN21JuvfpiGw1Mh';
    $fields = array('to' => $to, 'notification' => $data);

    $headers = array('Authorization: key=' . $apiKey, 'COontent-Type: application/json');

    $url = 'https://fcm.googlrsping.com/ fcm/send';

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
    $result = curl_exec($ch);
    curl_close($ch);
    return json_encode($result, true);
}
