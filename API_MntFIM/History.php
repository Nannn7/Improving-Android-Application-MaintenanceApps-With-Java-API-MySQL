<?php
require_once 'Connection.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $response = array();

    $query = "SELECT * FROM history";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $history = array();

        while ($row = mysqli_fetch_assoc($result)) {
            $history_item = array(
                'ID' => $row['ID'],
                'PIC' => $row['PIC'],
                'line' => $row['line'],
                'mesin' => $row['mesin'],
                'masalah' => $row['masalah'],
                'start' => $row['start'],
                'end' => $row['end'],
                'penyebab' => $row['penyebab'],
                'penanganan' => $row['penanganan'],
                'SparePart' => $row['SparePart'],
                'tanggal' => $row['tanggal'],
                'tanggal_selesai' => $row['tanggal_selesai'],
                'nomor_mesin' => $row['nomor_mesin'],
                'proses' => $row['proses']
            );

            array_push($history, $history_item);
        }

        $response['kode'] = 1;
        $response['pesan'] = 'Data ditemukan';
        $response['history'] = $history;
    } else {
        $response['kode'] = 0;
        $response['pesan'] = 'Data tidak ditemukan';
    }

    echo json_encode($response);
}
?>
