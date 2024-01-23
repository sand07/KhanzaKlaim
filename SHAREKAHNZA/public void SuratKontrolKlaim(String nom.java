public void SuratKontrolKlaim(String nomorrawat, String nomorrm) {

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String kodedokter = Sequel.cariIsi("select skdp_bpjs_new.kd_dokter from skdp_bpjs_new where skdp_bpjs_new.no_rawat='" + nomorrawat + "'");
        String kodepoli = Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat='" + nomorrawat + "'");
        String kodepj = Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat='" + nomorrawat + "'");
        String tglperiksa = Sequel.cariIsi("select skdp_bpjs_new.tanggal_periksakembali from skdp_bpjs_new where skdp_bpjs_new.no_rawat='" + nomorrawat + "'");
        String antrianbooking = Sequel.cariIsi("SELECT\n"
                + "	concat('A',booking_registrasi.no_reg)\n"
                + "FROM\n"
                + "	booking_registrasi where booking_registrasi.no_rkm_medis='" + nomorrm + "' and booking_registrasi.kd_dokter='" + kodedokter + "' and booking_registrasi.kd_poli='" + kodepoli + "' and booking_registrasi.tanggal_periksa='" + tglperiksa + "'");

        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("antrianbooking", antrianbooking);
        if (kodepj.equals("BPJ")) {
            param.put("tanggalrujukan", Sequel.cariIsi("SELECT	concat('No SEP :', bridging_sep.no_sep,' Tanggal Rujukan :', DATE_FORMAT(bridging_sep.tglrujukan,'%d-%m-%Y'),' Berlaku sampai : ',DATE_FORMAT(DATE_ADD(bridging_sep.tglrujukan, INTERVAL 89 DAY),'%d-%m-%Y')) FROM bridging_sep where bridging_sep.no_rawat='" + nomorrawat + "' "));
        } else {
            param.put("tanggalrujukan", "");
        }
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        Valid.MyReportqry("rptSuratKontrol.jasper", "report", "::[ Surat Kontrol ]::",
                "SELECT DISTINCT\n"
                + "	pasien.no_rkm_medis, \n"
                + "	pasien.nm_pasien, \n"
                + "	pasien.jk, \n"
                + "	pasien.tgl_lahir, \n"
                + "	skdp_bpjs_new.diagnosa, \n"
                + "	skdp_bpjs_new.tl, \n"
                + "	penjab.kd_pj, \n"
                + "	penjab.png_jawab, \n"
                + "	skdp_bpjs_new.tanggal_surat, \n"
                + "	skdp_bpjs_new.tanggal_periksakembali, \n"
                + "	pegawai.nik, \n"
                + "	pegawai.nama, pegawai.jbtn \n"
                + "FROM\n"
                + "	skdp_bpjs_new\n"
                + "	INNER JOIN\n"
                + "	pasien\n"
                + "	ON \n"
                + "		skdp_bpjs_new.no_rkm_medis = pasien.no_rkm_medis\n"
                + "	INNER JOIN\n"
                + "	pegawai\n"
                + "	ON \n"
                + "		skdp_bpjs_new.kd_dokter = pegawai.nik\n"
                + "	INNER JOIN\n"
                + "	reg_periksa\n"
                + "	ON \n"
                + "		pasien.no_rkm_medis = reg_periksa.no_rkm_medis\n"
                + "	INNER JOIN\n"
                + "	penjab\n"
                + "	ON \n"
                + "		skdp_bpjs_new.kd_pj = penjab.kd_pj"
                + " where skdp_bpjs_new.no_rawat='" + nomorrawat + "'", param);
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void SuratKontrolKlaimPDF(String nomorrawat, String nomorrm) {

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String kodedokter = Sequel.cariIsi("select skdp_bpjs_new.kd_dokter from skdp_bpjs_new where skdp_bpjs_new.no_rawat='" + nomorrawat + "'");
        String kodepoli = Sequel.cariIsi("select reg_periksa.kd_poli from reg_periksa where reg_periksa.no_rawat='" + nomorrawat + "'");
        String kodepj = Sequel.cariIsi("select reg_periksa.kd_pj from reg_periksa where reg_periksa.no_rawat='" + nomorrawat + "'");
        String tglperiksa = Sequel.cariIsi("select skdp_bpjs_new.tanggal_periksakembali from skdp_bpjs_new where skdp_bpjs_new.no_rawat='" + nomorrawat + "'");
        String antrianbooking = Sequel.cariIsi("SELECT\n"
                + "	concat('A',booking_registrasi.no_reg)\n"
                + "FROM\n"
                + "	booking_registrasi where booking_registrasi.no_rkm_medis='" + nomorrm + "' and booking_registrasi.kd_dokter='" + kodedokter + "' and booking_registrasi.kd_poli='" + kodepoli + "' and booking_registrasi.tanggal_periksa='" + tglperiksa + "'");

        Map<String, Object> param = new HashMap<>();
        param.put("namars", akses.getnamars());
        param.put("alamatrs", akses.getalamatrs());
        param.put("kotars", akses.getkabupatenrs());
        param.put("propinsirs", akses.getpropinsirs());
        param.put("kontakrs", akses.getkontakrs());
        param.put("emailrs", akses.getemailrs());
        param.put("antrianbooking", antrianbooking);
        if (kodepj.equals("BPJ")) {
            param.put("tanggalrujukan", Sequel.cariIsi("SELECT	concat('No SEP :', bridging_sep.no_sep,' Tanggal Rujukan :', DATE_FORMAT(bridging_sep.tglrujukan,'%d-%m-%Y'),' Berlaku sampai : ',DATE_FORMAT(DATE_ADD(bridging_sep.tglrujukan, INTERVAL 89 DAY),'%d-%m-%Y')) FROM bridging_sep where bridging_sep.no_rawat='" + nomorrawat + "' "));
        } else {
            param.put("tanggalrujukan", "");
        }
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        Valid.MyReportqrypdfKlaim("rptSuratKontrol.jasper", "report", "5SURKONRS",
                "SELECT DISTINCT\n"
                + "	pasien.no_rkm_medis, \n"
                + "	pasien.nm_pasien, \n"
                + "	pasien.jk, \n"
                + "	pasien.tgl_lahir, \n"
                + "	skdp_bpjs_new.diagnosa, \n"
                + "	skdp_bpjs_new.tl, \n"
                + "	penjab.kd_pj, \n"
                + "	penjab.png_jawab, \n"
                + "	skdp_bpjs_new.tanggal_surat, \n"
                + "	skdp_bpjs_new.tanggal_periksakembali, \n"
                + "	pegawai.nik, \n"
                + "	pegawai.nama, pegawai.jbtn \n"
                + "FROM\n"
                + "	skdp_bpjs_new\n"
                + "	INNER JOIN\n"
                + "	pasien\n"
                + "	ON \n"
                + "		skdp_bpjs_new.no_rkm_medis = pasien.no_rkm_medis\n"
                + "	INNER JOIN\n"
                + "	pegawai\n"
                + "	ON \n"
                + "		skdp_bpjs_new.kd_dokter = pegawai.nik\n"
                + "	INNER JOIN\n"
                + "	reg_periksa\n"
                + "	ON \n"
                + "		pasien.no_rkm_medis = reg_periksa.no_rkm_medis\n"
                + "	INNER JOIN\n"
                + "	penjab\n"
                + "	ON \n"
                + "		skdp_bpjs_new.kd_pj = penjab.kd_pj"
                + " where skdp_bpjs_new.no_rawat='" + nomorrawat + "'", param, "hasilkompilasiklaim", nomorrawat.replaceAll("/", ""));
        this.setCursor(Cursor.getDefaultCursor());
    }