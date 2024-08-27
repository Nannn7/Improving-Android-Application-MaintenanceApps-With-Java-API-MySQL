<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id_alarm = $_POST['id_alarm'];
    // $id_alarm = "8";

    $query = "SELECT D.ID, D.Definisi, D.ID_Alarm
                FROM mnt_md_Definisi as D
                WHERE D.ID_Alarm = '$id_alarm'";

    $result = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));
    $array = array();

    while ($row = mysqli_fetch_assoc($result)) {
        $array[] = $row;
    }

    echo ($result) ?
        json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "result" => $array)) :
        json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
}
