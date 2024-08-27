<?php
require_once 'Connection.php';

$query = "SELECT ID, Nama FROM sis_ms_Line ORDER By Nama";

$result = mysqli_query($conn, $query);
// printf("Error: %s\n", mysqli_error($conn));
$array = array();

while ($row = mysqli_fetch_assoc($result)) {
    $array[] = $row;
}

echo ($result) ?
    json_encode(array("kode" => 1, "pesan" => "Data ditemukan", "result" => $array)) :
    json_encode(array("kode" => 0, "pesan" => "Data tidak ditemukan"));
