<?php
session_start();
require_once('conf/conf.php');
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
header("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
header("Cache-Control: no-store, no-cache, must-revalidate");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache"); // HTTP/1.0
date_default_timezone_set("Asia/Bangkok");
$tanggal = mktime(date("m"), date("d"), date("Y"));
$jam = date("H:i");
?>
<!doctype html>
<html lang="en">

<head>
    <title>Antrian Farmasi</title>
    <link rel="icon" href="assets/img/rs.png" type="image/x-icon">
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <meta http-equiv="cache-control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="pragma" content="no-cache">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <!-- Global style END -->

</head>

<body>
    <?php
    if ($_GET['jkdokter'] == "L") {
        $jkpemanggil = "Indonesian Male";
    } else {
        $jkpemanggil = "Indonesian Female";
    }

    $namapasienpanggil = strtolower($_GET['namapasien']);
    $namapolipanggil = strtolower($_GET['namapoliklinik']);
    ?>
    <div>
        <td><button class="badge badge-success" onclick='responsiveVoice.speak("Pasien atas nama <?php echo $namapasienpanggil . ', dengan nomor antrian.' ?> <?php echo $_GET['nomorantri'] . ', silahkan ke ' ?><?php echo $namapolipanggil . '.' ?> ","Indonesian Female", {pitch: 1.0,rate: 0.9,volume: 5})'>Panggil Pasien</button></td>
        <td><button class="badge badge-success" onclick='responsiveVoice.speak("Pasien atas nama <?php echo $namapasienpanggil . ', silahkan ke admin poliklinik.' ?>","Indonesian Female", {pitch: 1.0,rate: 0.9,volume: 5})'><i class="fa fa-info-circle"></i>Panggil Admin Poli</button></td>

        <!-- <td><button class="badge badge-success" onclick="speakTextAdminPoli()"><i class="fa fa-info-circle"></i>Coba</button></td> -->

    </div>
    <script type="text/javascript" src="assets/js/responsivevoice.js"></script>
    <script>
        function speakTextAdminPoli() {
            // Create a new SpeechSynthesisUtterance
            var utterance = new SpeechSynthesisUtterance();

            // Set the text you want to speak in Indonesian
            utterance.text = "Pasien atas nama, <?php echo $namapasienpanggil ?> ,silahkan ke admin poliklinik";

            // Set the language to Indonesian
            utterance.lang = "id-ID";

            // Specify the desired voice (you may need to check available voices)
            var voices = window.speechSynthesis.getVoices();
            var selectedVoice = voices.find(function(voice) {
                return voice.lang === "id-ID" && voice.name.includes("female");
            });

            // Set the selected voice
            utterance.voice = selectedVoice;

            // Add the utterance to the speech synthesis queue
            window.speechSynthesis.speak(utterance);
            console.log(utterance.text);
            // alert("The value entered is: " + utterance.text);
        }

        // Wait for voices to be loaded before setting up the voice
        window.speechSynthesis.onvoiceschanged = function() {
            // You may want to put this inside a callback to ensure voices are available
            // before the user interacts with the page.
        };
    </script>
    <!-- <script src="https://code.responsivevoice.org/responsivevoice.js?key=zMl7FSvp"></script> -->
    <!-- <script type="text/javascript" src="assets/js/responsivevoice.js"></script> -->
</body>

</html>