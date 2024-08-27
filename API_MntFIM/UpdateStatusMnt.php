<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];
    $pic = $_POST['idPIC'];
    $status = $_POST['status'];
    // $id = "6";
    // $pic = "1";
    // $status = "1";

    date_default_timezone_set("Asia/Jakarta");
    $now = date("Y-m-d H:i:s");

    $query = "UPDATE mnt_tr_Maintenance 
                set PIC = '$pic', TimeStart = '$now', Status = '$status'
                where ID = '$id'";

    $result1 = mysqli_query($conn, $query);
    // printf("Error: %s\n", mysqli_error($conn));

    echo ($result1) ?
        json_encode(array("kode" => 1, "pesan" => "Data berhasil diubah")) :
        json_encode(array("kode" => 0, "pesan" => "Data gagal diubah"));
}
