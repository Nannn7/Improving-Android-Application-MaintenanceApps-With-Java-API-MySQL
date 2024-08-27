<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id_bagian_mesin'];
    // $id = "2";

    $query = "SELECT S.*
            FROM mnt_ms_BagianMesin as B
            JOIN mnt_ms_Sparepart as S ON B.ID = S.ID_BagianMesin
            WHERE B.ID = '$id'
            ORDER BY Nama";

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
