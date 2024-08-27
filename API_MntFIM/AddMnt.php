<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $mesin = $_POST['idMesin'];
    $pic = $_POST['idPIC'];
    $status = $_POST['status'];
    // $mesin = "2CBB4D1C88F8";
    // $pic = "1";
    // $status = "1";

    date_default_timezone_set("Asia/Jakarta");
    $now = date("Y-m-d H:i:s");

    $query = "INSERT INTO mnt_tr_Maintenance (ID_Mesin, PIC, TimeError, TimeStart, TimeStop, Status)
                VALUES ('$mesin', '$pic', '0000-00-00 00:00:00', '$now', '0000-00-00 00:00:00', '$status')";

    $result1 = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));

    echo ($result1) ?
        json_encode(array("kode" => 1, "pesan" => "Data maintenance berhasil ditambahkan")) :
        json_encode(array("kode" => 0, "pesan" => "Data maintenance gagal ditambahkan"));
}
