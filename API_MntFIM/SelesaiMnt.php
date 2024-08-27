<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idMnt = isset($_POST['idMnt']) ? $_POST['idMnt'] : null;
    $masalah = isset($_POST['idMasalah']) ? $_POST['idMasalah'] : null;
    $penyebab = isset($_POST['idPenyebab']) ? $_POST['idPenyebab'] : null;
    $sparepart = isset($_POST['idSparepart']) ? $_POST['idSparepart'] : null;
    $sppmwh = isset($_POST['sppmwh']) ? $_POST['sppmwh'] : null;
    $idLine = isset($_POST['idLine']) ? $_POST['idLine'] : null;
    $idMesin = isset($_POST['idMesin']) ? $_POST['idMesin'] : null;
    $jenisMnt = isset($_POST['jenisMnt']) ? $_POST['jenisMnt'] : null;
    // $idMnt = "23";
    // $masalah = null;
    // $penyebab = "77";
    // $sparepart = "null";
    // $sppmwh = "-";
    $status = "2";

    date_default_timezone_set("Asia/Jakarta");
    $now = date("Y-m-d H:i:s");

    $query = "UPDATE mnt_tr_Maintenance set ";

    if ($masalah == "null" || $masalah == null) $query .= "ID_Masalah = null, ";
    else $query .= "ID_Masalah = '$masalah', ";

    if ($penyebab == "null" || $penyebab == null) $query .= "ID_PenyebabAlarm = null, ";
    else $query .= "ID_PenyebabAlarm = '$penyebab', ";

    if ($sparepart == "null" || $sparepart == null) $query .= "ID_Sparepart = null, ";
    else $query .= "ID_Sparepart = '$sparepart', ";

    $query .= "SPPM_WH = '$sppmwh', TimeStop = '$now', Status = '$status' where ID = '$idMnt'";

    $result1 = mysqli_query($conn, $query);
    if ($jenisMnt == "0") { // breakdown
        $result = mysqli_query($conn, "CALL errMesinEnd('$idLine', '$idMesin')");
        // printf("Error: %s\n", mysqli_error($conn));

        echo ($result) ?
            json_encode(array("kode" => 1, "pesan" => "Data berhasil diubah")) :
            json_encode(array("kode" => 0, "pesan" => "Data gagal diubah"));
    } else if ($jenisMnt == "1") { // preventive

        echo ($result1) ?
            json_encode(array("kode" => 1, "pesan" => "Data berhasil diubah")) :
            json_encode(array("kode" => 0, "pesan" => "Data gagal diubah"));
    }
}
