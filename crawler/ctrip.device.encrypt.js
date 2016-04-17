   // w is encryptID;
   w = "{EncryptID}";
 
    //  function J() {
    //     try {
    //         var b = [
    //         ],
    //         c;
    //         for (c in D) if (D.hasOwnProperty(c)) {
    //             var t;
    //             var a = D[c];
    //             t = (F.isComponentInstalled(a, 'componentid') ? F.getComponentVersion(a, 'componentid').replace((/,/g), '.')  : null);
    //             t && b.push(c + ':' + ((t ? ('' + t).slice(0, K[c])  : '')))
    //         }
    //         return b.join(',')
    //     } catch (B) {
    //         return ''
    //     }
    // }
   function encrypt(b, c) {
        if ('number' == typeof c) c = [
            c
        ];
         else {
            for (var t = [
            ], a = 0, B = c.length; a < B; a++) t[a] = c.charCodeAt(a);
            c = t
        }
        t = [
        ];
        a = 0;
        for (B = b.length; a < B; a++) {
            var m = b.charCodeAt(a),
            g = c[a % c.length];
            t[a] = String.fromCharCode(m ^ g || g)
        }
        return t.join('')
    }
    // l = {};
    // l.Acrobat = 0;
    // l.SilverLight = 0;
    // l.ShockWave = 0;
    // l.Realplayer  = 0;
    // l.MediaPlayer = 0;
    // l.Java = 0;
    //      l = {
    //         u_id: function () {
    //             var b = document.cookie.match((/(^|;\s*)_bfa=\d+\.([^\.;]+\.[^\.;]+)\./));
    //             return (b ? (b[2] || '').toString().slice(0, 39)  : '')
    //         }(),
    //         u_localZone: function () {
    //             var b = (new Date).getTimezoneOffset() / 60;
    //             return ((0 > b ? '+' : '-')) + Math.abs(b)
    //         }(),
    //         u_screen: screen.width + 'x' + screen.height,
    //         u_colorDep: screen.colorDepth,
    //         u_osVer: (navigator.platform || '').toString().slice(0, 21),
    //         u_osLang: (navigator.language || navigator.systemLanguage || '').toString().slice(0, 12),
    //         u_browserVer: function () {
    //             for (var b = [
    //                 (/(MSIE|Firefox|Chrome|Safari|Opera).+?([\d\.]+)/),
    //                 (/(OmniWeb|iCab|KDE|Camino).+?([\d\.]+)/),
    //                 (/(WebKit|Mozilla|Netscape|Gecko).+?([\d\.]+)/)
    //             ], c = navigator.userAgent, l = 0; H = b.length; l++) {
    //                 var a = c.match(b[l]);
    //                 if (a) return (a[1] + '\t' + a[2]).slice(0, 30)
    //             }
    //             return 'Unknow'
    //         }(),
    //         u_browserLang: (navigator.language || navigator.userLanguage || '').toString().slice(0, 10),
    //         u_pdfVer: (l.Acrobat || '').toString().slice(0, 9),
    //         u_plug: 'SilverLight:5.1.30514.0',
    //         u_play: 'ShockWave:' + (l.ShockWave || '').toString().slice(0, 10) + ',MediaPlayer:' + (l.MediaPlayer || '').toString().slice(0, 22) + ',Realplayer:' + (l.Realplayer || '').toString().slice(0, 11) + ',QuickTime:' + (l.QuickTime || '').toString().slice(0, 5),
    //         u_java: (l.Java || '').toString().slice(0, 8),
    //         u_component: J()
    //     },
        // console.log(l);
        //u_id|1460613256338.269cjb@u_localZone|+8@u_screen|1366x768@u_colorDep|24@u_osVer|Win32@u_osLang|en-US@u_browserVer|Firefox 45.0@u_browserLang|en-US@u_pdfVer|@u_plug|SilverLight:5.1.30514.0@u_play|ShockWave:,MediaPlayer:,Realplayer:,QuickTime:@u_java|@u_component|
        z = [],
        // v;
        v = "u_id|{vid}@u_localZone|+8@u_screen|1366x768@u_colorDep|24@u_osVer|Win32@u_osLang|en-US@u_browserVer|Firefox 45.0@u_browserLang|en-US@u_pdfVer|@u_plug|SilverLight:5.1.30514.0@u_play|ShockWave:,MediaPlayer:,Realplayer:,QuickTime:@u_java|@u_component|";
        // for (v in l) 
        //     l.hasOwnProperty(v) && z.push(v + '|' + l[v]);
        // v = z.join('@');
        //console.log(v);
        for (var z = l = 0, H = w.length; z < H; z++) 
            l += w.charCodeAt(z);
      //M is device Id
        M = encrypt(encrypt(v, l % 256), w);
        // console.log(M)
        // return M;