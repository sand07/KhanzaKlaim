public void RadPdfKlaimBPJS(String NomorRawat, String NoRekamMedis) {
        pemeriksaan = "";
        try {
            ps2 = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	permintaan_radiologi.noorder, \n"
                    + "	periksa_radiologi.no_rawat, \n"
                    + "	permintaan_radiologi.tgl_permintaan, \n"
                    + "	permintaan_radiologi.jam_permintaan, \n"
                    + "	permintaan_radiologi.tgl_sampel, \n"
                    + "	permintaan_radiologi.jam_sampel, \n"
                    + "	permintaan_radiologi.tgl_hasil, \n"
                    + "	permintaan_radiologi.jam_hasil, \n"
                    + "	periksa_radiologi.kd_dokter, \n"
                    + "	permintaan_radiologi.diagnosa_klinis, \n"
                    + "	periksa_radiologi.kd_jenis_prw, \n"
                    + "	jns_perawatan_radiologi.nm_perawatan, \n"
                    + "	hasil_radiologi.hasil, \n"
                    + "	reg_periksa.no_rkm_medis\n"
                    + "FROM\n"
                    + "	permintaan_radiologi\n"
                    + "	INNER JOIN\n"
                    + "	periksa_radiologi\n"
                    + "	ON \n"
                    + "		permintaan_radiologi.no_rawat = periksa_radiologi.no_rawat\n"
                    + "	INNER JOIN\n"
                    + "	jns_perawatan_radiologi\n"
                    + "	ON \n"
                    + "		periksa_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw\n"
                    + "	INNER JOIN\n"
                    + "	hasil_radiologi\n"
                    + "	ON \n"
                    + "		periksa_radiologi.no_rawat = hasil_radiologi.no_rawat\n"
                    + "	INNER JOIN\n"
                    + "	reg_periksa\n"
                    + "	ON \n"
                    + "		hasil_radiologi.no_rawat = reg_periksa.no_rawat AND\n"
                    + "		periksa_radiologi.no_rawat = reg_periksa.no_rawat AND\n"
                    + "		permintaan_radiologi.no_rawat = reg_periksa.no_rawat where periksa_radiologi.no_rawat='" + NomorRawat + "' \n"
                    + "		group by permintaan_radiologi.noorder,periksa_radiologi.no_rawat");
            try {
//                ps2.setString(1, NomorRawat);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
//                    pemeriksaan = rs2.getString("nm_perawatan") + "\n" + pemeriksaan;
//                    kdpenjab = rs2.getString("kd_dokter");
//                    kdpetugas = rs2.getString("nip");
//                    System.out.println("ps mencari hasil radiologi" + rs2.getString("no_rawat"));
                    pscetakradiologi1 = koneksi.prepareStatement("SELECT\n"
                            + "	permintaan_radiologi.noorder, \n"
                            + "	jns_perawatan_radiologi.nm_perawatan, \n"
                            + "	permintaan_radiologi.tgl_sampel, \n"
                            + "	permintaan_radiologi.jam_sampel, \n"
                            + "	hasil_radiologi.tgl_periksa, \n"
                            + "	hasil_radiologi.jam, \n"
                            + "	hasil_radiologi.hasil, \n"
                            + "	hasil_radiologi.hasil\n"
                            + "FROM\n"
                            + "	periksa_radiologi\n"
                            + "	INNER JOIN\n"
                            + "	jns_perawatan_radiologi\n"
                            + "	ON \n"
                            + "		periksa_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw\n"
                            + "	INNER JOIN\n"
                            + "	permintaan_radiologi\n"
                            + "	ON \n"
                            + "		permintaan_radiologi.no_rawat = periksa_radiologi.no_rawat AND\n"
                            + "		periksa_radiologi.tgl_periksa = permintaan_radiologi.tgl_hasil AND\n"
                            + "		periksa_radiologi.jam = permintaan_radiologi.jam_hasil\n"
                            + "	INNER JOIN\n"
                            + "	dokter\n"
                            + "	ON \n"
                            + "		periksa_radiologi.dokter_perujuk = dokter.kd_dokter\n"
                            + "	INNER JOIN\n"
                            + "	hasil_radiologi\n"
                            + "	ON \n"
                            + "		periksa_radiologi.no_rawat = hasil_radiologi.no_rawat AND\n"
                            + "		periksa_radiologi.tgl_periksa = hasil_radiologi.tgl_periksa AND\n"
                            + "		periksa_radiologi.jam = hasil_radiologi.jam \n"
                            + "		\n"
                            + "		where periksa_radiologi.no_rawat='" + rs2.getString("no_rawat") + "'");

                    try {
                        rscetakradiologi1 = pscetakradiologi1.executeQuery();
                        int nomorurut = 0;

                        while (rscetakradiologi1.next()) {
                            nomorurut++;
//                            System.out.println("nomor urut " + nomorurut);
                            Map<String, Object> param = new HashMap<>();
                            param.put("noperiksa", NomorRawat);
                            param.put("norm", rs2.getString("no_rkm_medis"));
                            param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='" + rs2.getString("no_rkm_medis") + "'"));
                            param.put("jkel", Sequel.cariIsi("select jk from pasien where no_rkm_medis='" + rs2.getString("no_rkm_medis") + "'"));
                            param.put("tgl_lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis='" + rs2.getString("no_rkm_medis") + "'"));
                            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis='" + rs2.getString("no_rkm_medis") + "'"));
                            param.put("pengirim", Sequel.cariIsi("SELECT dokter.nm_dokter FROM periksa_radiologi INNER JOIN dokter ON periksa_radiologi.dokter_perujuk = dokter.kd_dokter WHERE periksa_radiologi.no_rawat = ?", rs2.getString("no_rawat")));
                            param.put("tanggal", rscetakradiologi1.getString("tgl_sampel"));
                            param.put("penjab", Sequel.cariIsi("SELECT\n"
                                    + "	dokter.nm_dokter\n"
                                    + "FROM\n"
                                    + "	dokter\n"
                                    + "	INNER JOIN\n"
                                    + "	set_pjlab\n"
                                    + "	ON \n"
                                    + "		dokter.kd_dokter = set_pjlab.kd_dokterrad"));
                            param.put("petugas", "");
                            param.put("alamat", "");
                            param.put("kamar", "");
                            param.put("namakamar", "");
                            param.put("pemeriksaan", rscetakradiologi1.getString("nm_perawatan"));
                            //param.put("jam", tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                            param.put("jam", rscetakradiologi1.getString("jam_sampel"));
                            param.put("namars", akses.getnamars());
                            param.put("alamatrs", akses.getalamatrs());
                            param.put("kotars", akses.getkabupatenrs());
                            param.put("propinsirs", akses.getpropinsirs());
                            param.put("kontakrs", akses.getkontakrs());
                            param.put("emailrs", akses.getemailrs());
                            param.put("hasil", rscetakradiologi1.getString("hasil"));
                            param.put("logo", Sequel.cariGambar("select logo from setting"));
                            param.put("logokars", Sequel.cariGambar("select logokars from setting"));
                            param.put("jam_hasil", rscetakradiologi1.getString("jam"));
                            param.put("diagnosa_klinis", rs2.getString("diagnosa_klinis"));
                            finger = Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kdpenjab);
                            param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + Sequel.cariIsi("SELECT\n"
                                    + "	dokter.nm_dokter\n"
                                    + "FROM\n"
                                    + "	dokter\n"
                                    + "	INNER JOIN\n"
                                    + "	set_pjlab\n"
                                    + "	ON \n"
                                    + "		dokter.kd_dokter = set_pjlab.kd_dokterrad") + "\nID " + (finger.equals("") ? kdpenjab : finger) + "\n" + rscetakradiologi1.getString("tgl_periksa"));
                            finger = Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kdpetugas);
                            param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + petugas + "\nID " + (finger.equals("") ? kdpetugas : finger) + "\n" + rscetakradiologi1.getString("tgl_periksa"));
                            String hasil = Sequel.cariIsi("select hasil from hasil_radiologi where hasil_radiologi.no_rawat='" + NomorRawat + "' and hasil_radiologi.tgl_periksa='" + rscetakradiologi1.getString("tgl_periksa") + "' and hasil_radiologi.jam='" + rscetakradiologi1.getString("jam") + "'").toString();

                            if (hasil.contains("<p>") || hasil.contains("<strong>") || hasil.contains("<ul>")) {
                                Valid.MyReport("rptPeriksaRadiologiHtml.jasper", "report", "RAD", param);
                            } else {
                                Valid.MyReport("rptPeriksaRadiologi.jasper", "report", "RAD", param);
                            }

                        }
                    } catch (Exception e) {

                    } finally {
                        if (rscetakradiologi1 != null) {
                            rscetakradiologi1.close();
                        }
                        if (pscetakradiologi1 != null) {
                            pscetakradiologi1.close();
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgCariPeriksaRadiologi.BtnPrint1ActionPerformed() ps2 : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi Pemeriksaan : " + e);
        }

        this.setCursor(Cursor.getDefaultCursor());

    }

    public void RadPdfKlaimBPJSKompilasi(String NomorRawat, String NoRekamMedis) {
        pemeriksaan = "";
        try {
            ps2 = koneksi.prepareStatement(
                    "SELECT\n"
                    + "	permintaan_radiologi.noorder, \n"
                    + "	periksa_radiologi.no_rawat, \n"
                    + "	permintaan_radiologi.tgl_permintaan, \n"
                    + "	permintaan_radiologi.jam_permintaan, \n"
                    + "	permintaan_radiologi.tgl_sampel, \n"
                    + "	permintaan_radiologi.jam_sampel, \n"
                    + "	permintaan_radiologi.tgl_hasil, \n"
                    + "	permintaan_radiologi.jam_hasil, \n"
                    + "	periksa_radiologi.kd_dokter, \n"
                    + "	permintaan_radiologi.diagnosa_klinis, \n"
                    + "	periksa_radiologi.kd_jenis_prw, \n"
                    + "	jns_perawatan_radiologi.nm_perawatan, \n"
                    + "	hasil_radiologi.hasil, \n"
                    + "	reg_periksa.no_rkm_medis\n"
                    + "FROM\n"
                    + "	permintaan_radiologi\n"
                    + "	INNER JOIN\n"
                    + "	periksa_radiologi\n"
                    + "	ON \n"
                    + "		permintaan_radiologi.no_rawat = periksa_radiologi.no_rawat\n"
                    + "	INNER JOIN\n"
                    + "	jns_perawatan_radiologi\n"
                    + "	ON \n"
                    + "		periksa_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw\n"
                    + "	INNER JOIN\n"
                    + "	hasil_radiologi\n"
                    + "	ON \n"
                    + "		periksa_radiologi.no_rawat = hasil_radiologi.no_rawat\n"
                    + "	INNER JOIN\n"
                    + "	reg_periksa\n"
                    + "	ON \n"
                    + "		hasil_radiologi.no_rawat = reg_periksa.no_rawat AND\n"
                    + "		periksa_radiologi.no_rawat = reg_periksa.no_rawat AND\n"
                    + "		permintaan_radiologi.no_rawat = reg_periksa.no_rawat where periksa_radiologi.no_rawat='" + NomorRawat + "' \n"
                    + "		group by permintaan_radiologi.noorder,periksa_radiologi.no_rawat");
            try {
//                ps2.setString(1, NomorRawat);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
//                    pemeriksaan = rs2.getString("nm_perawatan") + "\n" + pemeriksaan;
//                    kdpenjab = rs2.getString("kd_dokter");
//                    kdpetugas = rs2.getString("nip");
//                    System.out.println("ps mencari hasil radiologi" + rs2.getString("no_rawat"));
                    pscetakradiologi1 = koneksi.prepareStatement("SELECT\n"
                            + "	permintaan_radiologi.noorder, \n"
                            + "	jns_perawatan_radiologi.nm_perawatan, \n"
                            + "	permintaan_radiologi.tgl_sampel, \n"
                            + "	permintaan_radiologi.jam_sampel, \n"
                            + "	hasil_radiologi.tgl_periksa, \n"
                            + "	hasil_radiologi.jam, \n"
                            + "	hasil_radiologi.hasil, \n"
                            + "	hasil_radiologi.hasil\n"
                            + "FROM\n"
                            + "	periksa_radiologi\n"
                            + "	INNER JOIN\n"
                            + "	jns_perawatan_radiologi\n"
                            + "	ON \n"
                            + "		periksa_radiologi.kd_jenis_prw = jns_perawatan_radiologi.kd_jenis_prw\n"
                            + "	INNER JOIN\n"
                            + "	permintaan_radiologi\n"
                            + "	ON \n"
                            + "		permintaan_radiologi.no_rawat = periksa_radiologi.no_rawat AND\n"
                            + "		periksa_radiologi.tgl_periksa = permintaan_radiologi.tgl_hasil AND\n"
                            + "		periksa_radiologi.jam = permintaan_radiologi.jam_hasil\n"
                            + "	INNER JOIN\n"
                            + "	dokter\n"
                            + "	ON \n"
                            + "		periksa_radiologi.dokter_perujuk = dokter.kd_dokter\n"
                            + "	INNER JOIN\n"
                            + "	hasil_radiologi\n"
                            + "	ON \n"
                            + "		periksa_radiologi.no_rawat = hasil_radiologi.no_rawat AND\n"
                            + "		periksa_radiologi.tgl_periksa = hasil_radiologi.tgl_periksa AND\n"
                            + "		periksa_radiologi.jam = hasil_radiologi.jam \n"
                            + "		\n"
                            + "		where periksa_radiologi.no_rawat='" + rs2.getString("no_rawat") + "'");

                    try {
                        rscetakradiologi1 = pscetakradiologi1.executeQuery();
                        int nomorurut = 0;

                        while (rscetakradiologi1.next()) {
                            nomorurut++;
//                            System.out.println("nomor urut " + nomorurut);
                            Map<String, Object> param = new HashMap<>();
                            param.put("noperiksa", NomorRawat);
                            param.put("norm", rs2.getString("no_rkm_medis"));
                            param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='" + rs2.getString("no_rkm_medis") + "'"));
                            param.put("jkel", Sequel.cariIsi("select jk from pasien where no_rkm_medis='" + rs2.getString("no_rkm_medis") + "'"));
                            param.put("tgl_lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis='" + rs2.getString("no_rkm_medis") + "'"));
                            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis='" + rs2.getString("no_rkm_medis") + "'"));
                            param.put("pengirim", Sequel.cariIsi("SELECT dokter.nm_dokter FROM periksa_radiologi INNER JOIN dokter ON periksa_radiologi.dokter_perujuk = dokter.kd_dokter WHERE periksa_radiologi.no_rawat = ?", rs2.getString("no_rawat")));
                            param.put("tanggal", rscetakradiologi1.getString("tgl_sampel"));
                            param.put("penjab", Sequel.cariIsi("SELECT\n"
                                    + "	dokter.nm_dokter\n"
                                    + "FROM\n"
                                    + "	dokter\n"
                                    + "	INNER JOIN\n"
                                    + "	set_pjlab\n"
                                    + "	ON \n"
                                    + "		dokter.kd_dokter = set_pjlab.kd_dokterrad"));
                            param.put("petugas", "");
                            param.put("alamat", "");
                            param.put("kamar", "");
                            param.put("namakamar", "");
                            param.put("pemeriksaan", rscetakradiologi1.getString("nm_perawatan"));
                            //param.put("jam", tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                            param.put("jam", rscetakradiologi1.getString("jam_sampel"));
                            param.put("namars", akses.getnamars());
                            param.put("alamatrs", akses.getalamatrs());
                            param.put("kotars", akses.getkabupatenrs());
                            param.put("propinsirs", akses.getpropinsirs());
                            param.put("kontakrs", akses.getkontakrs());
                            param.put("emailrs", akses.getemailrs());
                            param.put("hasil", rscetakradiologi1.getString("hasil"));
                            param.put("logo", Sequel.cariGambar("select logo from setting"));
                            param.put("logokars", Sequel.cariGambar("select logokars from setting"));
                            param.put("jam_hasil", rscetakradiologi1.getString("jam"));
                            param.put("diagnosa_klinis", rs2.getString("diagnosa_klinis"));
                            finger = Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kdpenjab);
                            param.put("finger", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + Sequel.cariIsi("SELECT\n"
                                    + "	dokter.nm_dokter\n"
                                    + "FROM\n"
                                    + "	dokter\n"
                                    + "	INNER JOIN\n"
                                    + "	set_pjlab\n"
                                    + "	ON \n"
                                    + "		dokter.kd_dokter = set_pjlab.kd_dokterrad") + "\nID " + (finger.equals("") ? kdpenjab : finger) + "\n" + rscetakradiologi1.getString("tgl_periksa"));
                            finger = Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?", kdpetugas);
                            param.put("finger2", "Dikeluarkan di " + akses.getnamars() + ", Kabupaten/Kota " + akses.getkabupatenrs() + "\nDitandatangani secara elektronik oleh " + petugas + "\nID " + (finger.equals("") ? kdpetugas : finger) + "\n" + rscetakradiologi1.getString("tgl_periksa"));
                            String hasil = Sequel.cariIsi("select hasil from hasil_radiologi where hasil_radiologi.no_rawat='" + NomorRawat + "' and hasil_radiologi.tgl_periksa='" + rscetakradiologi1.getString("tgl_periksa") + "' and hasil_radiologi.jam='" + rscetakradiologi1.getString("jam") + "'").toString();

                            if (hasil.contains("<p>") || hasil.contains("<strong>") || hasil.contains("<ul>")) {
                                Valid.MyReportPDFKlaim("rptPeriksaRadiologiHtml.jasper", "report", "4RAD", param, "hasilkompilasiklaim", NomorRawat.replaceAll("/", "") + "-" + nomorurut);

                            } else {
                                Valid.MyReportPDFKlaim("rptPeriksaRadiologi.jasper", "report", "4RAD", param, "hasilkompilasiklaim", NomorRawat.replaceAll("/", "") + "-" + nomorurut);

                            }
//                            Valid.MyReportPDFKlaim("rptPeriksaRadiologi.jasper", "report", "4RAD", param, "hasilkompilasiklaim", NomorRawat.replaceAll("/", "") + "-" + nomorurut);

                        }
                    } catch (Exception e) {

                    } finally {
                        if (rscetakradiologi1 != null) {
                            rscetakradiologi1.close();
                        }
                        if (pscetakradiologi1 != null) {
                            pscetakradiologi1.close();
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgCariPeriksaRadiologi.BtnPrint1ActionPerformed() ps2 : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi Pemeriksaan : " + e);
        }

        this.setCursor(Cursor.getDefaultCursor());

    }


    public void CetakSPRIKlaim(String nomorsep, String nomorrawat) {
        String nomorsurat = Sequel.cariIsi("select bridging_surat_pri_bpjs.no_surat from bridging_surat_pri_bpjs where bridging_surat_pri_bpjs.no_rawat='" + nomorrawat + "'");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
        param.put("parameter", nomorrawat);
        Valid.MyReportqry("rptBridgingSuratPRI2.jasper", "report", "::[ Data Surat PRI VClaim ]::",
                "select bridging_surat_pri_bpjs.no_rawat,bridging_surat_pri_bpjs.no_kartu,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"
                + "pasien.jk,bridging_surat_pri_bpjs.diagnosa,bridging_surat_pri_bpjs.tgl_surat,bridging_surat_pri_bpjs.no_surat,"
                + "bridging_surat_pri_bpjs.tgl_rencana,bridging_surat_pri_bpjs.kd_dokter_bpjs,bridging_surat_pri_bpjs.nm_dokter_bpjs,"
                + "bridging_surat_pri_bpjs.kd_poli_bpjs,bridging_surat_pri_bpjs.nm_poli_bpjs from reg_periksa inner join bridging_surat_pri_bpjs "
                + "on bridging_surat_pri_bpjs.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                + "where bridging_surat_pri_bpjs.no_surat='" + nomorsurat + "'", param);
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void CetakSPRIKlaimPDF(String nomorsep, String nomorrawat) {
        String nomorsurat = Sequel.cariIsi("select bridging_surat_pri_bpjs.no_surat from bridging_surat_pri_bpjs where bridging_surat_pri_bpjs.no_rawat='" + nomorrawat + "'");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
        param.put("parameter", nomorrawat);
        Valid.MyReportqrypdfKlaim("rptBridgingSuratPRI2.jasper", "report", "2SPRI",
                "select bridging_surat_pri_bpjs.no_rawat,bridging_surat_pri_bpjs.no_kartu,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,"
                + "pasien.jk,bridging_surat_pri_bpjs.diagnosa,bridging_surat_pri_bpjs.tgl_surat,bridging_surat_pri_bpjs.no_surat,"
                + "bridging_surat_pri_bpjs.tgl_rencana,bridging_surat_pri_bpjs.kd_dokter_bpjs,bridging_surat_pri_bpjs.nm_dokter_bpjs,"
                + "bridging_surat_pri_bpjs.kd_poli_bpjs,bridging_surat_pri_bpjs.nm_poli_bpjs from reg_periksa inner join bridging_surat_pri_bpjs "
                + "on bridging_surat_pri_bpjs.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                + "where bridging_surat_pri_bpjs.no_surat='" + nomorsurat + "'", param, "hasilkompilasiklaim", nomorrawat.replaceAll("/", ""));
        this.setCursor(Cursor.getDefaultCursor());
    }


    