<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id_definisi = $_POST['id_definisi'];
    // $id_definisi = "11";

    $query = "SELECT F.ID, F.Foto
                FROM mnt_md_DefinisiFoto as F
                WHERE F.ID_Definisi = '$id_definisi'";

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
