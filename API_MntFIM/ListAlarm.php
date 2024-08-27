<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id_Series = $_POST['id_series'];
    // $id_Series = "35";

    $query = "SELECT A.ID, A.Display, A.Nama, S.NamaSeries, J.NamaJenis
                FROM mnt_md_Alarm as A
                JOIN mnt_md_SeriesKontrol as S ON S.ID = A.ID_SeriesKontrol
                JOIN mnt_md_JenisKontrol as J ON J.ID = S.ID_JenisKontrol
                WHERE A.ID_SeriesKontrol = '$id_Series'
                ORDER By Display ASC, NamaSeries ASC";

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
