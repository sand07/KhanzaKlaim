public void HasilLabPDFKlaimBPJS(String NomorRawat, String NoRekamMedis) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            ps4 = koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"
                    + "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "
                    + " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "
                    + "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where periksa_lab.kategori='PK' and "
                    + " periksa_lab.no_rawat=? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam)");
            try {
                ps4.setString(1, NomorRawat);
                rs = ps4.executeQuery();
                while (rs.next()) {
                    kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='" + rs.getString("no_rawat") + "' order by tgl_masuk desc limit 1");
                    if (!kamar.equals("")) {
                        namakamar = kamar + ", " + Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "
                                + " where kamar.kd_kamar='" + kamar + "' ");
                        kamar = "Kamar";
                    } else if (kamar.equals("")) {
                        kamar = "Poli";
                        namakamar = Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "
                                + "where reg_periksa.no_rawat='" + rs.getString("no_rawat") + "'");
                    }
                    Map<String, Object> param = new HashMap<>();
                    param.put("noperiksa", rs.getString("no_rawat"));
                    param.put("norm", rs.getString("no_rkm_medis") + " | Tgl Lhr : " + rs.getString("lahir"));
                    param.put("namapasien", rs.getString("nm_pasien"));
                    param.put("jkel", rs.getString("jk"));
                    param.put("umur", rs.getString("umur"));
                    param.put("pengirim", Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", rs.getString("dokter_perujuk")));
                    param.put("tanggal", rs.getString("tgl_periksa"));
                    param.put("penjab", rs.getString("nm_dokter"));
                    param.put("petugas", rs.getString("nama"));
                    param.put("jam", rs.getString("jam"));
                    param.put("alamat", rs.getString("alamat"));
                    param.put("kamar", kamar);
                    param.put("namakamar", namakamar);
                    finger = Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("kd_dokter"));
                    param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nm_dokter") + "\nID " + (finger.equals("") ? rs.getString("kd_dokter") : finger) + "\n" + rs.getString("tgl_periksa"));
                    finger = Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", rs.getString("nip"));
                    param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + rs.getString("nama") + "\nID " + (finger.equals("") ? rs.getString("nip") : finger) + "\n" + rs.getString("tgl_periksa"));
                    Sequel.queryu("truncate table temporary_lab");

                    ps2 = koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "
                            + "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.kategori='PK' and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "
                            + "and periksa_lab.jam=?");
                    try {
                        ps2.setString(1, rs.getString("no_rawat"));
                        ps2.setString(2, Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps2.setString(3, rs.getString("jam"));
                        rs2 = ps2.executeQuery();
                        urutan = 0;
                        while (rs2.next()) {
                            urutan++;
                            Sequel.menyimpan("temporary_lab", "'0','" + rs2.getString("nm_perawatan") + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Data User");
                            ps3 = koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"
                                    + "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "
                                    + "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                            try {
                                ps3.setString(1, rs.getString("no_rawat"));
                                ps3.setString(2, rs2.getString("kd_jenis_prw"));
                                ps3.setString(3, Valid.SetTgl(rs.getString("tgl_periksa")));
                                ps3.setString(4, rs.getString("jam"));
                                rs3 = ps3.executeQuery();
                                while (rs3.next()) {
                                    Sequel.menyimpan("temporary_lab", "'0','  " + rs3.getString("Pemeriksaan") + "','" + rs3.getString("nilai").replaceAll("'", "`") + "','" + rs3.getString("satuan")
                                            + "','" + rs3.getString("nilai_rujukan") + "','" + rs3.getString("keterangan") + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Data User");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif ps3 : " + e);
                            } finally {
                                if (rs3 != null) {
                                    rs3.close();
                                }
                                if (ps3 != null) {
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif ps2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }

                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    param.put("logokars", Sequel.cariGambar("select logokars from setting"));
                    pspermintaan = koneksi.prepareStatement(
                            "select noorder,DATE_FORMAT(tgl_permintaan,'%d-%m-%Y') as tgl_permintaan,jam_permintaan from permintaan_lab where "
                            + "no_rawat=? and tgl_hasil=? and jam_hasil=?");
                    try {
                        pspermintaan.setString(1, rs.getString("no_rawat"));
                        pspermintaan.setString(2, Valid.SetTgl(rs.getString("tgl_periksa")));
                        pspermintaan.setString(3, rs.getString("jam"));
                        rspermintaan = pspermintaan.executeQuery();
                        if (rspermintaan.next()) {
                            param.put("nopermintaan", rspermintaan.getString("noorder"));
                            param.put("tanggalpermintaan", rspermintaan.getString("tgl_permintaan"));
                            param.put("jampermintaan", rspermintaan.getString("jam_permintaan"));
                            Valid.MyReport("rptPeriksaLabPermintaan.jasper", "report", "PERIKSALAB", param);
                        } else {
                            Valid.MyReport("rptPeriksaLab.jasper", "report", "PERIKSALAB", param);
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    } finally {
                        if (rspermintaan != null) {
                            rspermintaan.close();
                        }
                        if (pspermintaan != null) {
                            pspermintaan.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif ps4 : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        this.setCursor(Cursor.getDefaultCursor());
    }