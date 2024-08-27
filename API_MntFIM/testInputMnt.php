<?php
require_once 'Connection.php';

// if ($_SERVER['REQUEST_METHOD'] == 'POST') {
$idMesin = "12C84B479B54";
$status = "0";

date_default_timezone_set("Asia/Jakarta");
$now = date("Y-m-d H:i:s");

$query = "INSERT INTO mnt_tr_Maintenance (ID_Mesin, TimeError, TimeStart, TimeStop, Status)
                VALUES ('$idMesin', '$now', '0000-00-00 00:00:00', '0000-00-00 00:00:00', '$status')";

$result1 = mysqli_query($conn, $query);
// printf("Error: %s\n", mysqli_error($conn));

echo ($result1) ?
    json_encode(array("kode" => 1, "pesan" => "Data berhasil input")) :
    json_encode(array("kode" => 0, "pesan" => "Data gagal input"));
// }
