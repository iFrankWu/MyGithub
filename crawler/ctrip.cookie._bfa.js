// window = {};
// window.document = {};
// window.location = {};
// window.location.protocol = "http";
// window.document.cookie = "";
window.replace = function() {
    return ''
};

var cookieFun = function(f, C) {
  
        if (!f.$_bf || !0 !== f.$_bf.loaded) {
            f.$_bf = f.$_bf || {};
            f.$_bf.version = '2.3.8';
            f.$_bf.loaded = !0;
            var p = f.document,
                v = p.location,
                c = c || {},
                I = Object.prototype.toString,
                J = Object.prototype.hasOwnProperty,
                w = function() {},
                D = {
                    undefined: 'undefined',
                    number: 'number',
                    'boolean': 'boolean',
                    string: 'string',
                    '[object Function]': 'function',
                    '[object RegExp]': 'regexp',
                    '[object Array]': 'array',
                    '[object Date]': 'date',
                    '[object Error]': 'error',
                    '[object Object]': 'object'
                };
            c.PROTOCOL = (-1 != v.protocol.indexOf('file:') ?
                'http:' : v.protocol);
            c.ENV = f.$_bf.env || 'online';
            c.ishttps = ('https:' == c.PROTOCOL ? !0 : !1);
            c.CFG = {
                domain: v.hostname.split('.').slice(-2).join('.') || 'ctrip.com',
                referrer: !1,
                cv1: !1,
                orderID: '',
                cookiePath: '/',
                surl: c.PROTOCOL + '//s.c-ctrip.com/',
                href: v.href,
                dCollect: {},
                delay: 0,
                debug: !1
            };
            c.now = Date.now || function() {
                return (new Date).getTime()
            };
            c.type = function(a) {
                return D[typeof a] || D[I.call(a)] || ((a ? 'object' : 'null'))
            };
            c.isArray = function(a) {
                return 'array' === c.type(a)
            };
            c.isFunction = function(a) {
                return 'function' === c.type(a)
            };
            c.isNumber = function(a) {
                return 'number' === typeof a && isFinite(a)
            };
            c.isEmpty = function(a) {
                return a === C || '-' == a || '' == a
            };
            c.getRand = function() {
                return ('' + Math.random()).slice(-8)
            };
            c.keys = function(a) {
                if (Object.keys) return Object.keys(a);
                var b = [],
                    d;
                for (d in a) J.call(a, d) && b.push(d);
                return b
            };
            c.extend = function() {
                var a,
                    b,
                    d,
                    e = arguments[0] || {},
                    c = 1,
                    q = arguments.length;
                q === c && (e = this, --c);
                for (; c < q; c++)
                    if (null != (d = arguments[c]))
                        for (b in d) a = d[b],
                            e !== a && a !== C && (e[b] = a);
                return e
            };
            c.hash = function(a) {
                var b = 1,
                    d = 0,
                    e;
                if (!c.isEmpty(a))
                    for (b =
                        0, e = a.length - 1; 0 <= e; e--) d = a.charCodeAt(e),
                        b = (b << 6 & 268435455) + d + (d << 14),
                        d = b & 266338304,
                        b = (0 != d ? b ^ d >> 21 : b);
                return b
            };
            c.$ = function(a) {
                return p.getElementById(a)
            };
            c.$v = function(a) {
                return (a = c.$(a)) && a.value || ''
            };
            c.encode = function(a) {
                return encodeURIComponent(a)
            };
            c.decode = function(a) {
                return decodeURIComponent(a)
            };
            c.contains = function(a, b) {
                return -1 < a.indexOf(b)
            };
            c.getCookie = function(a, b, d) {
                return ((a = p.cookie.match(RegExp('(^| )' + a + '=([^;]*)(;|$)'))) ? (d ? c.decode(a[2]) : a[2]) : b || '')
            };
            c.getCookieObj = function(a, b) {
                var d = {
                        __k: a
                    },
                    e = [],
                    l;
                if (e = c.getCookie(a, '', b))
                    for (var e = e.split('&') || [], q = 0; q < e.length; q++) l = e[q].split('='),
                        1 < l.length && (d[l[0]] = l[1]);
                return d
            };
            //cookiename,cookievalue,expiredTime
            c.setCookie = function(a, b, d) {
                var e = (c.CFG.domain ? ';domain=' + c.CFG.domain : ''),
                    l = '';
                0 <= d && (l = ';expires=' + (new Date(c.now() + d)).toUTCString());
                console.log("Set cookie:"+a + '=' + c.encode(b) + e + ';path=/' + l)
                p.cookie = a + '=' + c.encode(b) + e + ';path=/' + l;
            };
            c.getItem = function(a) {};
            c.get = function(a, b) {
                var d,
                    e = {};
                b = b || {};
                e.v = b.v || '';
                e.w = b.w || 'default';
                e.l = b.l || 0;
                e.t = b.t || 'string';
                switch (e.w) {
                    case 'input':
                        d = c.$v(a);
                        break;
                    case 'cookie':
                        d = c.getCookie(a, e.v);
                        break;
                    case 'function':
                        d = (c.isFunction(e.v) ? e.v() : '');
                        break;
                    case 'object':
                        break;
                    default:
                        d = e.v
                }
                switch (e.t) {
                    case 'number':
                        return parseInt(e.v, 10) || 0;
                    case 'boolean':
                        return !!d;
                    default:
                        return (e.l ? String(d).substring(0, e.l) : d)
                }
            };
            c.getXpath = function(a) {
                for (var b = [], d = 0; a;) {
                    var e = b,
                        c = d++,
                        q = a.nodeName,
                        n;
                    a: {
                        n = 0;
                        if (a.parentNode)
                            for (var g = a.parentNode.firstChild; g;) {
                                if (g == a) {
                                    n = (0 == n ? '' : '[' + (n + 1) + ']');
                                    break a
                                }
                                1 == g.nodeType && g.tagName == a.tagName && n++;
                                g = g.nextSibling
                            }
                        n = ''
                    }
                    e[c] = q + n;
                    if ('HTML' != a.tagName.toUpperCase()) a =
                        a.parentNode;
                    else break
                }
                b = b.reverse();
                return b.join('/')
            };
            c.CLI = function() {
                var a = f.screen,
                    b = f.navigator,
                    d = (a ? a.colorDepth + '-bit' : ''),
                    e = ((b && b.language ? b.language : (b && b.browserLanguage ? b.browserLanguage : ''))).toLowerCase(),
                    l = (b && b.javaEnabled() ? 1 : 0);
                return {
                    s: a,
                    c: d,
                    l: e,
                    j: l,
                    getRefer: function() {
                        if (c.CFG.referrer) return c.CFG.referrer;
                        var a = '';
                        try {
                            a = p && p.referrer
                        } catch (b) {}
                        if (!a) try {
                            f.opener && (a = f.opener.location && f.opener.location.href)
                        } catch (e) {}
                        c.CFG.referrer = String(a).substring(0, 500);
                        return c.CFG.referrer
                    },
                    getHash: function() {
                        //b --> navigator
                        for (var q = f.history.length, n = [
                                b.appName,
                                b.appVersion,
                                e,
                                b.platform,
                                b.userAgent,
                                l,
                                a.width + a.height,
                                d,
                                (p.cookie ? p.cookie : ''),
                                (p.referrer ? p.referrer : '')
                            ].join(''), g = n.length; 0 < q;) n += q-- ^ g++;
                        return c.hash(n)
                    }
                }
            }();
            c.send = function(a, b) {
                var d = (c.CFG.debug ? 'http://localhost/bf.gif' : c.CFG.surl + 'bf.gif'),
                    d = d + ('?' + a + '&mt=' + c.now() + '&jv=2.3.8');
                c.isFunction(b) || (b = w);
                var e = (new Image);
                e.width = 1;
                e.height = 1;
                e.onload = function() {
                    e = e.onerror = e.onload = null;
                    b(1)
                };
                e.onerror = function() {
                    e = e.onerror = e.onload =
                        null;
                    b(0)
                };
                e.src = d
            };
            c.on = function() {
                return (p.addEventListener ? function(a, b, d, e) {
                        a.addEventListener(b, d, e || !1)
                    } :
                    (p.attachEvent ? function(a, b, d) {
                            a.attachEvent('on' + b, d)
                        } :
                        w))
            }();
            c.isSupportStorage = function() {
                var a = !1;
                if ('undefined' != typeof Storage) try {
                    f.localStorage.setItem('_tmptest', 'tmpval'),
                        a = !0,
                        f.localStorage.removeItem('_tmptest')
                } catch (b) {}
                return (a ? !0 : !1)
            }();
            c.isSupportCookie = !0;
            (function(a) {
                function b(a) {
                    var b = 0,
                        e = [],
                        d = 0;
                    this.n = function() {
                        if (0 == d) {
                            if (b >= a.length) return null;
                            var c = a.charCodeAt(b++);
                            if (127 >= c) return c;
                            if (2047 >= c) return e[1] = 128 | c & 63,
                                d = 1,
                                192 | c >> 6 & 31;
                            if (55296 <= c && 57343 >= c) throw 'unsupported char code.';
                            e[0] = 128 | c >> 6 & 63;
                            e[1] = 128 | c & 63;
                            d = 2;
                            return 224 | c >> 12 & 15
                        }
                        return e[e.length - d--]
                    }
                }

                function d(a) {
                    var b = new function(a) {
                        for (var b = [], e = -1, c; null != (c = a.n());)
                            if (0 == c[0]) {
                                if (-1 == e || 255 == b[e]) b.push(128),
                                    e = b.length - 1;
                                b.push(c[1]);
                                b[e]++
                            } else {
                                e = -1;
                                b.push(c[0]);
                                var d = c[1];
                                if (127 >= d) b.push(d);
                                else {
                                    for (c = []; 0 < d;) c.push(d & 127),
                                        d >>= 7;
                                    for (d = c.length - 1; 0 <= d; d--) b.push(c[d] | 128);
                                    b[b.length - 1] &= 127
                                }
                            }
                        e =
                            0;
                        this.n = function() {
                            return (e >= b.length ? null : b[e++])
                        }
                    }(new function(a) {
                        function b(a, e) {
                            for (var d = 0, c = 0; c < n; c++) d = 131 * d + a[e + c];
                            return d
                        }
                        for (var e = [], d = [], g = [], m, k = 0, f, h; null != (m = a.n());) g.push(m);
                        this.n = function() {
                            if (k < g.length - n + 1) {
                                for (var a = b(g, k), m = h = f = 0; m < d.length; m++)
                                    if (a == d[m]) {
                                        var u,
                                            s;
                                        u = m;
                                        for (s = k; u < e.length && s < g.length && !(e[u] != g[s] || s - k >= q); u++, s++);
                                        u - m >= h && u - m >= n && (h = u - m, f = m)
                                    }
                                if (0 == h) return a = [
                                        0,
                                        g[k]
                                    ],
                                    m = g[k++],
                                    e.push(m),
                                    e.length > c && (e.shift(), d.shift()),
                                    e.length >= n && (m = e.length - n, d[m] = b(e, m)),
                                    a;
                                k += h;
                                return [h,
                                    e.length - (f + h)
                                ]
                            }
                            return (k < g.length ? [0,g[k++]] : null)
                        }
                    }(a));
                    this.n = function() {
                        return b.n()
                    }
                }

                function e(a) {
                    var b = [],
                        e = 0;
                    this.n = function() {
                        if (0 < e) return b[4 - e--];
                        var d = [
                            a.n(),
                            a.n(),
                            a.n()
                        ];
                        if (null == d[0]) return null;
                        b[0] = g.charAt(d[0] >> 2);
                        b[1] = (null == d[1] ? g.charAt(d[0] << 4 & 63) : g.charAt((d[0] << 4 | d[1] >> 4) & 63));
                        b[2] = (null == d[1] ? '~' : (null == d[2] ? g.charAt(d[1] << 2 & 63) : g.charAt((d[1] << 2 | d[2] >> 6) & 63)));
                        b[3] = (null == d[2] ? '~' : g.charAt(d[2] & 63));
                        e = 3;
                        return b[0]
                    }
                }
                var c = 1024,
                    q = 63,
                    n = 3,
                    g = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_';
                a.compress = function(a) {
                    a = new b(a);
                    a = new d(a);
                    a = new e(a);
                    for (var c, g = []; null != (c = a.n());) g[g.length] = c;
                    return g.join('')
                }
            })(c);
            c._union_ = !0;
            c.fingerprint = function() {
                try {
                    var a = (/([0-9.\s]+)/g),
                        b = {
                            debug: !1,
                            version: '1.0.1',
                            vid: '',
                            info: [],
                            hash: function(a, b) {
                                var d,
                                    c,
                                    g,
                                    m,
                                    k;
                                d = a.length & 3;
                                c = a.length - d;
                                g = b || 31;
                                for (k = 0; k < c;) m = a.charCodeAt(k) & 255 | (a.charCodeAt(++k) & 255) << 8 | (a.charCodeAt(++k) & 255) << 16 | (a.charCodeAt(++k) & 255) << 24,
                                    ++k,
                                    m = 3432918353 * (m & 65535) + ((3432918353 * (m >>> 16) & 65535) << 16) & 4294967295,
                                    m = m << 15 | m >>>
                                    17,
                                    m = 461845907 * (m & 65535) + ((461845907 * (m >>> 16) & 65535) << 16) & 4294967295,
                                    g ^= m,
                                    g = g << 13 | g >>> 19,
                                    g = 5 * (g & 65535) + ((5 * (g >>> 16) & 65535) << 16) & 4294967295,
                                    g = (g & 65535) + 27492 + (((g >>> 16) + 58964 & 65535) << 16);
                                m = 0;
                                switch (d) {
                                    case 3:
                                        m ^= (a.charCodeAt(k + 2) & 255) << 16;
                                    case 2:
                                        m ^= (a.charCodeAt(k + 1) & 255) << 8;
                                    case 1:
                                        m ^= a.charCodeAt(k) & 255,
                                            m = 3432918353 * (m & 65535) + ((3432918353 * (m >>> 16) & 65535) << 16) & 4294967295,
                                            m = m << 15 | m >>> 17,
                                            g ^= 461845907 * (m & 65535) + ((461845907 * (m >>> 16) & 65535) << 16) & 4294967295
                                }
                                g ^= a.length;
                                g ^= g >>> 16;
                                g = 2246822507 * (g & 65535) + ((2246822507 * (g >>> 16) & 65535) << 16) & 4294967295;
                                g ^= g >>> 13;
                                g = 3266489909 * (g & 65535) + ((3266489909 * (g >>> 16) & 65535) << 16) & 4294967295;
                                return (g ^ g >>> 16) >>> 0
                            },
                            trim: function(b) {
                                return (b || '').replace(a, '')
                            },
                            display: function() {
                                var a = screen || null,
                                    b = [];
                                if (!a) return 'screen';
                                b.push(a.width + 'x' + a.height + '|' + a.colorDepth);
                                a.deviceYDPI && b.push('|DPI' + a.deviceXDPI + 'x' + a.deviceYDPI);
                                return b.join('')
                            },
                            timezone: function() {
                                return 'zone' + (new Date).getTimezoneOffset() + ''
                            },
                            os: function() {
                                var a = [],
                                    b = navigator;
                                f.maxConnectionsPerServer && a.push('maxcon' + (f.maxConnectionsPerServer || '0'));
                                b.hardwareConcurrency && a.push('hdcon' + b.hardwareConcurrency);
                                a.push(this.timezone());
                                a.push(this.display());
                                return a.join('')
                            },
                            agent: function() {
                                var a = navigator,
                                    b = [];
                                if (a.mimeTypes) {
                                    for (var d = a.mimeTypes, c = d.length || 0, g = '', m = 0; m < c; m++) var k = d[m],
                                        g = g + (k.type + k.suffixes + k.description);
                                    b.push(g)
                                }
                                if (a.plugins) {
                                    d = a.plugins;
                                    c = 'pls';
                                    for (g = 0; g < d.length && !(m = d[g], c += m.filename || m.name || g, 30 < g); g++);
                                    b.push(c)
                                }
                                b.push(a.userAgent);
                                b.push(a.language || a.userLanguage || a.browserLanguage ||
                                    '');
                                b.push(a.doNotTrack || '0');
                                b.push(a.platform || 'None');
                                return b.join('')
                            },
                            gl: function() {
                                var a = document.createElement('canvas');
                                if ('undefined' === typeof a.getContext) return !1;
                                a.width = 600;
                                a.height = 300;
                                a.style.border = '1px solid red';
                                var b = null,
                                    d = [
                                        'webgl',
                                        'experimental-webgl',
                                        'moz-webgl',
                                        'webkit-3d'
                                    ];
                                try {
                                    for (var c = 0; c < d.length && null === b; c++) b = a.getContext(d[c], {
                                        width: 600,
                                        height: 300
                                    });
                                    if (null === b) return !1
                                } catch (g) {}
                            },
                            canvas: function() {
                                var a = document.createElement('canvas');
                                if ('undefined' === typeof a.getContext) return !1;
                                a.width = 600;
                                a.height = 30;
                                a.style.border = '1px solid #3a3a3a';
                                var b = a.getContext('2d'),
                                    d = b.createLinearGradient(0, 0, 200, 0);
                                d.addColorStop(0, 'rgb(200,0,0)');
                                d.addColorStop(0.5, 'rgb(0,200,0)');
                                d.addColorStop(1, 'rgb(200,0,0)');
                                b.fillStyle = d;
                                b.fillRect(0, 0, 200, 30);
                                b.fillStyle = '#360';
                                b.font = '13px _sans';
                                b.textBaseLine = 'top';
                                b.fillText('English中文Հայերենا繁體輸入لعربيةҚазақша`~1!2@3#4$5%6^7&8*9(0)-_=+[{]}|;:\',<.>/?', 5, 18);
                                b.beginPath();
                                b.strokeStyle = 'blue';
                                b.lineWidth = 5;
                                b.shadowOffsetX = 2;
                                b.shadowOffsetY = 2;
                                b.shadowColor = 'rgb(85,85,85)';
                                b.shadowBlur = 3;
                                b.arc(500, 15, 10, 0, 2 * Math.PI, !0);
                                b.stroke();
                                b.closePath();
                                this.debug && document.body.appendChild(a);
                                return a.toDataURL() + a.toDataURL('image/jpeg')
                            },
                            init: function() {
                                var a = b.os(),
                                    d = b.agent(),
                                    c = this.canvas(),
                                    f = [];
                                f.push(this.hash(a));
                                f.push(this.hash(d));
                                c && f.push(this.hash(c));
                                a = [];
                                for (d = 0; d < f.length; d++) a.push(f[d].toString(36));
                                return this.vid = a.join('-')
                            }
                        };
                    return b.init()
                } catch (d) {}
            };
            (function() {
                function a(a) {
                    return (10 > a ? '0' + a : a)
                }

                function b(a) {
                    e.lastIndex = 0;
                    return (e.test(a) ? '"' + a.replace(e, function(a) {
                        var b = h[a];
                        return ('string' === typeof b ? b : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4))
                    }) + '"' : '"' + a + '"')
                }

                function d(a, c) {
                    var e,
                        h,
                        n,
                        p,
                        x = l,
                        s,
                        r = c[a];
                    r && 'object' === typeof r && 'function' === typeof r.toJSON && (r = r.toJSON(a));
                    'function' === typeof g && (r = g.call(c, a, r));
                    switch (typeof r) {
                        case 'string':
                            return b(r);
                        case 'number':
                            return (isFinite(r) ? String(r) : 'null');
                        case 'boolean':
                        case 'null':
                            return String(r);
                        case 'object':
                            if (!r) return 'null';
                            l += f;
                            s = [];
                            if ('[object Array]' === Object.prototype.toString.apply(r)) {
                                p = r.length;
                                for (e = 0; e < p; e += 1) s[e] = d(e, r) || 'null';
                                n = (0 === s.length ? '[]' : (l ? '[\n' + l + s.join(',\n' + l) + '\n' + x + ']' : '[' + s.join(',') + ']'));
                                l = x;
                                return n
                            }
                            if (g && 'object' === typeof g)
                                for (p = g.length, e = 0; e < p; e += 1) 'string' === typeof g[e] && (h = g[e], (n = d(h, r)) && s.push(b(h) + ((l ? ': ' : ':')) + n));
                            else
                                for (h in r) Object.prototype.hasOwnProperty.call(r, h) && (n = d(h, r)) && s.push(b(h) + ((l ? ': ' : ':')) + n);
                            n = (0 === s.length ? '{}' : (l ? '{\n' + l + s.join(',\n' +
                                l) + '\n' + x + '}' : '{' + s.join(',') + '}'));
                            l = x;
                            return n
                    }
                }
                if ('object' === typeof JSON && 'function' === typeof JSON.stringify) return c.JSON = {
                    stringify: JSON.stringify
                }, !0;
                'function' !== typeof Date.prototype.toJSON && (Date.prototype.toJSON = function() {
                        return (isFinite(this.valueOf()) ? this.getUTCFullYear() + '-' + a(this.getUTCMonth() + 1) + '-' + a(this.getUTCDate()) + 'T' + a(this.getUTCHours()) + ':' + a(this.getUTCMinutes()) + ':' + a(this.getUTCSeconds()) + 'Z' : null)
                    }, String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON =
                    function() {
                        return this.valueOf()
                    });
                var e = (/[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g),
                    l,
                    f,
                    h = {
                        '': '\\b',
                        '\t': '\\t',
                        '\n': '\\n',
                        '\f': '\\f',
                        '\r': '\\r',
                        '"': '\\"',
                        '\\': '\\\\'
                    },
                    g;
                c.JSON = {};
                c.JSON.stringify = function(a, b, c) {
                    var e;
                    f = l = '';
                    if ('number' === typeof c)
                        for (e = 0; e < c; e += 1) f += ' ';
                    else 'string' === typeof c && (f = c);
                    if ((g = b) && 'function' !== typeof b && ('object' !== typeof b || 'number' !== typeof b.length)) throw Error('JSON.stringify');
                    return d('', {
                        '': a
                    })
                }
            })();
            (function() {
                if (!(document.visibilityState || document.webkitVisibilityState || document.mozVisibilityState)) {
                    document.hidden = !1;
                    document.visibilityState = 'visible';
                    var a = null,
                        b = function() {
                            (document.createEvent ? (a || (a = document.createEvent('HTMLEvents'), a.initEvent('visibilitychange', !0, !0)), document.dispatchEvent(a)) : 'object' == typeof Visibility && Visibility._onChange.call(Visibility, {}))
                        },
                        d = function() {
                            document.hidden = !1;
                            document.visibilityState = 'visible';
                            b()
                        },
                        c = function() {
                            document.hidden = !0;
                            document.visibilityState =
                                'hidden';
                            b()
                        };
                    (document.addEventListener ? (f.addEventListener('focus', d, !0), f.addEventListener('blur', c, !0)) : (document.attachEvent('onfocusin', d), document.attachEvent('onfocusout', c)))
                }
            })();
            (function(a) {
                var b = f.Visibility = {
                    onVisible: function(a) {
                        if (!b.isSupported() || !b.hidden()) return a(),
                            b.isSupported();
                        var c = b.change(function(l, f) {
                            b.hidden() || (b.unbind(c), a())
                        });
                        return c
                    },
                    change: function(a) {
                        if (!b.isSupported()) return !1;
                        b._lastCallback += 1;
                        var c = b._lastCallback;
                        b._callbacks[c] = a;
                        b._setListener();
                        return c
                    },
                    unbind: function(a) {
                        delete b._callbacks[a]
                    },
                    afterPrerendering: function(a) {
                        if (!b.isSupported() || 'prerender' != b.state()) return a(),
                            b.isSupported();
                        var c = b.change(function(l, f) {
                            'prerender' != f && (b.unbind(c), a())
                        });
                        return c
                    },
                    hidden: function() {
                        return b._prop('hidden', !1)
                    },
                    state: function() {
                        return b._prop('visibilityState', 'visible')
                    },
                    isSupported: function() {
                        return b._prefix() != a
                    },
                    _doc: f.document,
                    _prefixes: [
                        'webkit',
                        'moz'
                    ],
                    _chechedPrefix: null,
                    _listening: !1,
                    _lastCallback: -1,
                    _callbacks: {},
                    _hiddenBefore: !1,
                    _init: function() {
                        b._hiddenBefore =
                            b.hidden()
                    },
                    _prefix: function() {
                        if (null !== b._chechedPrefix) return b._chechedPrefix;
                        if (b._doc.visibilityState != a) return b._chechedPrefix = '';
                        for (var c, e = 0; e < b._prefixes.length; e++)
                            if (c = b._prefixes[e] + 'VisibilityState', b._doc[c] != a) return b._chechedPrefix = b._prefixes[e]
                    },
                    _name: function(a) {
                        var c = b._prefix();
                        return ('' == c ? a : c + a.substr(0, 1).toUpperCase() + a.substr(1))
                    },
                    _prop: function(a, c) {
                        return (b.isSupported() ? b._doc[b._name(a)] : c)
                    },
                    _onChange: function(a) {
                        var c = b.state(),
                            l;
                        for (l in b._callbacks) b._callbacks[l].call(b._doc, a, c);
                        b._hiddenBefore = b.hidden()
                    },
                    _setListener: function() {
                        if (!b._listening) {
                            var a = b._prefix() + 'visibilitychange',
                                c = function() {
                                    b._onChange.apply(Visibility, arguments)
                                };
                            (b._doc.addEventListener ? b._doc.addEventListener(a, c, !1) : b._doc.attachEvent(a, c));
                            b._listening = !0;
                            b._hiddenBefore = b.hidden()
                        }
                    }
                };
                b._init()
            })();
            var y = {
                    _env: (/((test[a-z]?|dev|uat|ui|local)\.sh\.(ctrip|huixuan)travel)|(qa\.nt\.ctripcorp)|(127.0.0.1)|(localhost)/i),
                    _bfa: (/^\d.+(\.\d+){2,}$/),
                    _bfs: (/^\d+\.\d+$/),
                    _var: (/\$\{(\w+)\}/g)
                },
                t = function() {
                    this.isEventInit =
                        this.isPSSend = this.isPVSend = !1;
                    this.enterTime = c.now();
                    this.commonData = [];
                    this.eSkip = this.pid = 0;
                    this.envInit_();
                    this.init()
                };
            t.prototype = {
                constructor: t,
                _QUEUE: [],
                init: function() {
                    this.isPVSend = !1;
                    this.bfi = this.bfs = this.bfa = null;
                    this.isLogin = this._isNewSession = this._isNewVisitor = 0;
                    this.readBfa();
                    this.sessRead();
                    this.readBfi();
                    throw "ereor";

                },
                envInit_: function() {
                    var a = v.hostname;
                    ('test' == c.ENV || y._env.test(a) ? c.CFG.surl = c.PROTOCOL + '//ubt.uat.qa.nt.ctripcorp.com/' : 'offline' == c.ENV && (c.CFG.surl = c.PROTOCOL + '//ubt.sh.ctriptravel.com/'));
                    if (a = this.domainInit(a)) c.CFG.domain = a
                },
                inputInit: function() {
                    try {
                        var a = {},
                            b = this;
                        document.body.innerHTML.replace((/\bbf_ubt_([^ '"]*)/gi), function(d, l) {
                            var f = c.$v(d);
                            if (f) {
                                var h = l.split('_');
                                (h[0] && 'tl' == h[0] ? (('offline' == c.ENV ? 'callid' == h[1] && b.sendData({
                                    k: 'pvctm',
                                    v: '{"callid":"' + f + '"}'
                                }) : b._setPVCustom(h[1], f)), a[h[1]] = f) : (a[h[0]] = f, b.set_(h[0].toLowerCase(), f)))
                            }
                        });
                        'offline' == c.ENV && a.eid && b.sendData({
                            k: 'offline_order',
                            v: '{"uid":"' + (a.uid || '') + '","orderid":"' + (a.orderid || '') + '","callid":"' + a.callid +
                                '","eid":"' + a.eid + '","pid":${page_id},"order_type":"' + (a.ordertype || '') + '"}'
                        })
                    } catch (d) {}
                },
                domainInit: function(a) {
                    a = a || location.hostname;
                    if ((/^(\d|\.)+$/).test(a)) return a;
                    var b = '',
                        c = a.split('.').reverse(),
                        b = c[1] + '.' + c[0];
                    (/(?:\.co)m?\.\w{2,}$/i).test(a) && (b = c[2] + '.' + b);
                    return b
                },
                uniqueId_: function() {
                    var tmp = c.getRand() ^ c.CLI.getHash() & 2147483647;
                    return c.getRand() ^ c.CLI.getHash() & 2147483647
                },
                plus_: function(a) {
                    return parseInt(a, 10) + 1
                },
                setItem: function(a, b, d) {
                    if (c.isSupportCookie) c.setCookie(a, b, d);
                    else return !1
                },
                getItem: function(a, b, d) {
                    return (c.isSupportCookie ?
                        c.getCookie(a, b, !0) : '')
                },
                readBfa: function() {
                    var a = this.getItem('_bfa', '', !0);
                    a && y._bfa.test(a) && (a = a.split('.'), 6 < a.length && (this.bfa = a));
                    this.bfa || (a = this.enterTime, this.bfa = [
                        1,
                        a,
                        this.uniqueId_().toString(36),
                        1,
                        a,
                        a,
                        0,
                        0
                    ], this._isNewVisitor = 1)
                },
                sessRead: function() {
                    var a = this.getItem('_bfs', '', !0);
                    (
                        !this._isNewVisitor && a && y._bfs.test(a) ? 
                        (   
                            this.bfs = a.split('.'), 
                            this.bfs[1] = this.plus_(this.bfs[1])) 
                        : 
                        (this._isNewSession = 1, 
                            this.bfs = [ 1,1], 
                            this.bfa[4] = this.bfa[5], 
                            this.bfa[5] = this.enterTime, 
                            this.bfa[6] = this.plus_(this.bfa[6]))
                        );
                    this.bfa[7] = this.plus_(this.bfa[7]);
                    this.sessWrite()
                },
                sessWrite: function() {
                    this.setItem('_bfa', this.bfa.join('.'), 63072000000);
                    this.setItem('_bfs', this.bfs.join('.'), 1800000)
                },
                readBfi: function() {
                    var a, b, c = {};
                    if (a = this.getItem('_bfi', '', !0)) {
                        a = a.split('&') || [];
                        for (var e = 0; e < a.length; e++) b = a[e].split('='),
                            1 < b.length && (c[b[0]] = b[1])
                    }
                    this.ppi = (c && c.p1 ? c.p1 : 0);
                    this.ppv = (c && c.v1 ? c.v1 : 0);
                    this.bfi = c
                },
                updateBfi: function() {
                    if (this.isPVSend) {
                        var a = this.get_('pvid');
                        this.setItem('_bfi', 'p1=' + this.pid + '&p2=' + this.ppi + '&v1=' +
                            a + '&v2=' + this.ppv)
                    }
                },
                get_: function(a) {
                    if (this.bfa) {
                        var b = this.bfa;
                        switch (a) {
                            case 'vid':
                                return b[1] + '.' + b[2];
                            case 'sid':
                                return parseInt(b[6], 10) || 0;
                            case 'pvid':
                                return parseInt(b[7], 10) || 0;
                            case 'fullpv':
                                return b[1] + '.' + b[2] + '.' + b[6] + '.' + b[7];
                            default:
                                return ''
                        }
                    }
                },
                set_: function(a, b) {
                    switch (a) {
                        case 'domain':
                            this._setDomain(b);
                            break;
                        case 'clickv1':
                            this._setClickV1(b);
                            break;
                        case 'collect':
                            this._delCollect(b);
                            break;
                        case 'referrer':
                            this._setReferrer(b);
                            break;
                        case 'orderid':
                            this._setOrderID(b)
                    }
                },
                getPid: function(a) {
                    if (!this.pid ||
                        a) this.pid = parseInt(c.$v('page_id'), 10) || 0;
                    return this.pid
                },
                formatABtestValue: function(a) {
                    (/;$/).test(a) || (a += ';');
                    return a
                },
                getABtest: function() {
                    if (this.d_abtest) return this.d_abtest;
                    for (var a = c.$('ab_testing_tracker'), b = [], d = ''; a;) d += this.formatABtestValue(a.value),
                        b.push(a),
                        a.removeAttribute('id'),
                        a.removeAttribute('name'),
                        a = c.$('ab_testing_tracker');
                    for (a = 0; a < b.length; a++) b[a].setAttribute('id', 'ab_testing_tracker');
                    try {
                        b = '',
                            (b = document.location.hash) && -1 !== b.indexOf('abtest=') && (d += this.formatABtestValue(decodeURIComponent(b.replace((/.*(abtest=)/i), '').replace((/#.*/i), ''))))
                    } catch (e) {}
                    return this.d_abtest = d.substring(0, 280)
                },
                getPVparams: function() {
                    if (this.d_pvparams) return this.d_pvparams;
                    var a = c.getCookieObj('Session'),
                        b = c.getCookieObj('CtripUserInfo'),
                        d = c.getCookieObj('StartCity_Pkg'),
                        e = c.getCookieObj('Union'),
                        a = {
                            engine: (a && a.SmartLinkCode ? a.SmartLinkCode : ''),
                            keyword: (a && a.SmartLinkQuary ? a.SmartLinkQuary : ''),
                            start_city: (d && d.PkgStartCity ? d.PkgStartCity : ''),
                            alliance_id: (e && e.AllianceID ? e.AllianceID : ''),
                            alliance_sid: (e && e.SID ? e.SID : ''),
                            alliance_ouid: (e &&
                                e.OUID ? e.OUID : ''),
                            user_grade: (b && b.VipGrade ? b.VipGrade : ''),
                            duid: (b && b.U ? b.U : ''),
                            zdata: c.get('zdatactrip', {
                                w: 'cookie',
                                v: ''
                            }),
                            callid: c.get('bf_ubt_tl_callid', {
                                w: 'input',
                                v: ''
                            })
                        };
                    try {
                        if (!a.alliance_id && !a.alliance_sid && c.isSupportStorage && (e = localStorage.getItem('UNION'))) {
                            var f = JSON.parse(e);
                            (e = f.data) && (f.st || f.timeout) && (b = 0, (b = (f.st ? new Date(f.st) : new Date(f.timeout.replace((/-/g), '/')))) && b >= c.now() && (a.alliance_id = e.AllianceID || e.ALLIANCEID || '', a.alliance_sid = e.SID || '', a.alliance_ouid = e.OUID || ''))
                        }
                    } catch (h) {}
                    a.duid && (this.isLogin = 1);
                    return this.d_pvparams = a
                },
                getCommon: function(a) {
                    if (a || 4 > this.commonData.length) this.commonData = [
                        this.getPid(),
                        this.get_('vid'),
                        this.get_('sid'),
                        this.get_('pvid'),
                        c.get('CorrelationId', {
                            w: 'input',
                            v: ''
                        }),
                        this.getABtest() || '',
                        c.get('bf_ubt_offline_mid', {
                            w: 'input',
                            v: ''
                        }),
                        '2.3.8',
                        c.fingerprint()
                    ];
                    return this.commonData
                },
                getUinfo: function() {
                    var a = this.getPVparams(),
                        b = [],
                        d = '';
                    try {
                        d += 'cl=' + document.cookie.length,
                            d += ',ckl=' + (document.cookie.match((/[^=]+=[^;]*;?/g)) || []).length
                    } catch (e) {}
                    b[0] =
                        11;
                    b[1] = parseInt(this.ppi, 10) || 0;
                    b[2] = parseInt(this.ppv, 10) || 0;
                    b[3] = String(c.CFG.href).substring(0, 600);
                    b[4] = c.CLI.s.width;
                    b[5] = c.CLI.s.height;
                    b[6] = d;
                    b[7] = c.CLI.l;
                    b[8] = a.engine;
                    b[9] = a.keyword;
                    b[10] = c.CLI.getRefer();
                    b[11] = this.getABtest();
                    b[12] = this._isNewVisitor;
                    b[13] = this.isLogin;
                    b[14] = c.get('login_uid', {
                        w: 'cookie',
                        v: ''
                    });
                    b[15] = a.user_grade;
                    b[16] = c.get('corpid', {
                        w: 'cookie',
                        v: ''
                    });
                    b[17] = a.start_city;
                    b[18] = a.alliance_id;
                    b[19] = a.alliance_sid;
                    b[20] = a.alliance_ouid;
                    b[21] = c.CFG.orderID;
                    b[22] = a.duid;
                    b[23] =
                        a.zdata;
                    b[24] = a.callid;
                    b[25] = c.get('bid', {
                        w: 'cookie',
                        v: ''
                    });
                    b[26] = '';
                    b[27] = '';
                    b[28] = '';
                    b[29] = 'online';
                    b[30] = f.devicePixelRatio || 1;
                    b[31] = this._isNewSession;
                    b[32] = '';
                    return b
                },
                replaceParam: function(a) {
                    var b = {
                        duid: this.getPVparams().duid,
                        page_id: this.getPid(),
                        is_login: this.isLogin
                    };
                    return a.replace(y._var, function(a, c) {
                        return (c in b ? b[c] : a)
                    })
                },
                dataHandler: function(a) {
                    if ('string' == typeof a) return a;
                    if ('object' == typeof a) {
                        if (a.c && a.d) return a = {
                                c: this.getCommon(),
                                d: a.d
                            },
                            'ac=g&d=' + c.encode(c.JSON.stringify(a));
                        if (a.k && a.v) {
                            var b = this.getPVparams(),
                                d = c.encode(this.replaceParam(a.v));
                            return 'ac=tl&pi=' + this.getPid() + '&key=' + a.k + '&val=' + d + '&pv=' + this.get_('fullpv') + '&duid=' + b.duid + '&env=online&v=6'
                        }
                    }
                    return !1
                },
                sendData: function(a) {
                    if (this.isPVSend) {
                        var b = this.dataHandler(a);
                        b && c.send(b, a.callback || w)
                    } else this._QUEUE.push(a)
                },
                sendPV: function(a) {
                    var b = this;
                    if (this.isPVSend) return !0;
                    try {
                        var d = {
                                c: this.getCommon(!0),
                                d: {
                                    uinfo: this.getUinfo()
                                }
                            },
                            e = 'ac=g&d=' + c.encode(c.JSON.stringify(d));
                        c.send(e, function(d) {
                            d && (b.isPVSend = !0, b.queueSend_());
                            c.isFunction(a) && a(d)
                        })
                    } catch (f) {}
                },
                queueSend_: function() {
                    this.updateBfi();
                    var a = this._QUEUE,
                        b = a.length;
                    if (b) {
                        for (var d = 0; d < b; d++) {
                            var e = a[d],
                                f = this.dataHandler(e);
                            f && c.send(f, e.callback || w)
                        }
                        this._QUEUE = []
                    }
                    c.PVCTM && this.sendData({
                        k: 'pvctm',
                        v: c.JSON.stringify(c.PVCTM)
                    });
                    return !0
                },
                getPS: function() {
                    for (var a = 'navigationStart redirectStart unloadEventStart unloadEventEnd redirectEnd fetchStart domainLookupStart domainLookupEnd connectStart connectEnd requestStart responseStart responseEnd domLoading domInteractive domContentLoadedEventStart domContentLoadedEventEnd domComplete loadEventStart loadEventEnd'.split(' '), b = f.performance.timing, c = [
                            6
                        ], e = 0; e < a.length; e++) c.push(b[a[e]]);
                    c.push(f.performance.navigation.type || 0);
                    c.push(f.performance.navigation.redirectCount || 0);
                    return c
                },
                sendPS: function() {
                    if (!(c.CFG.dCollect.performance || !f.performance || 0 > this.getPid())) {
                        var a = 0,
                            b = this;
                        (function() {
                            if (f.performance.timing.loadEventEnd && !this.isPSSend) {
                                var d = {
                                        c: b.getCommon(),
                                        d: {
                                            ps: b.getPS()
                                        }
                                    },
                                    d = 'ac=g&d=' + c.encode(c.JSON.stringify(d));
                                c.send(d);
                                b.isPSSend = !0
                            } else 300 > a && (a++, setTimeout(arguments.callee, 300))
                        })()
                    }
                },
                bindSend: function(a) {
                    var b =
                        this;
                    if (this.isEventInit) return !0;
                    this.isEventInit = !0;
                    this.inputInit();
                    this.sendPV(a);
                    Visibility.change(function(a, c) {
                        'visible' == c && b.updateBfi()
                    });
                    this.sendPS()
                },
                getCommonObj: function() {
                    var a = this.getCommon();
                    return {
                        pid: a[0],
                        vid: a[1],
                        sid: a[2],
                        pvid: a[3],
                        ver: '2.3.8',
                        ifr: 0
                    }
                },
                pack: function(a, b) {
                    if ('restiming' !== b) return !1;
                    a = [
                        [1,
                            'ctrip'
                        ],
                        this.getCommonObj(), [
                            [
                                ['ubt',
                                    b,
                                    1
                                ],
                                a
                            ]
                        ]
                    ];
                    var d = 'a=z&d=' + c.compress(c.JSON.stringify(a)) + '&t=' + c.now();
                    c.send(d)
                },
                _asynRefresh: function(a, b) {
                    if ('object' == typeof a) {
                        'undefined' !=
                        typeof a.page_id && (this.pid = parseInt(a.page_id, 10) || 0);
                        'undefined' != typeof a.url && c.contains(a.url, 'http') && (c.CFG.href = a.url);
                        if ('undefined' != typeof a.orderid) this._setOrderID(a.orderid, !0);
                        else {
                            var d = c.$v('bf_ubt_orderid');
                            d && this._setOrderID(d, !0)
                        }
                        a.refer && this._setReferrer(a.refer)
                    }(this.isEventInit ? (this.init(), this.sendPV(b)) : this.bindSend(b))
                },
                _setEnv: function(a) {
                    c.ENV = a || 'online';
                    this.envInit_()
                },
                _setDebug: function(a) {
                    c.CFG.debug = !!a
                },
                _setDomain: function(a) {
                    c.CFG.domain = a
                },
                _setClickV1: function(a) {
                    c.CFG.cv1 =
                        a || ''
                },
                _delCollect: function(a) {},
                _setReferrer: function(a) {
                    c.CFG.referrer = a
                },
                _setOrderID: function(a, b) {
                    !a || c.CFG.orderID && !b || (c.CFG.orderID = a)
                },
                _setPVCustom: function(a, b) {
                    c.PVCTM || (c.PVCTM = {});
                    c.PVCTM[a] = b
                },
                _getFullPV: function(a) {
                    var b = this.get_('fullpv');
                    a && c.isFunction(a) && a(b);
                    return b
                },
                _tracklog: function(a, b, c) {
                    if ('string' != typeof b) return !1;
                    this.sendData({
                        k: a,
                        v: b,
                        callback: c || !1
                    })
                },
                _trackError: function(a, b) {
                    if ('object' != c.type(a)) return !1;
                    if (this.eSkip && 'undefined' == typeof a.skip) return this.eSkip = !1;
                    !0 === a.skip && (this.eSkip = !0);
                    for (var d = 'version message line file category framework time repeat islogin name column'.split(' '), e = [
                            7,
                            '',
                            0,
                            '',
                            '',
                            '',
                            c.now() - this.enterTime,
                            1,
                            this.isLogin,
                            '',
                            0
                        ], f = 1, h = d.length; f < h; f++) {
                        var n = d[f];
                        if (a[n]) {
                            var g = a[n] + '';
                            switch (n) {
                                case 'message':
                                case 'file':
                                    g = g.substring(0, 500);
                                    break;
                                case 'category':
                                case 'framework':
                                case 'name':
                                    g = g.substring(0, 100);
                                    break;
                                case 'time':
                                    g = parseInt(g, 10);
                                    break;
                                case 'column':
                                    g = parseInt(g, 10);
                                    break;
                                default:
                                    g = parseInt(g, 10) || 0
                            }
                            e[f] = g
                        }
                    }
                    d = '';
                    (a.stack ? d = a.stack : 'undefined' != typeof cQuery && c.isFunction(cQuery.trace) && (d = cQuery.trace(), c.isArray(d) && (d = d.join(''))));
                    d = d.slice(e.join('').length - 2000);
                    e.push(d);
                    this.sendData({
                        c: !0,
                        d: {
                            error: e
                        },
                        callback: b || !1
                    })
                },
                _trackUserBlock: function(a, b) {
                    if ('object' != typeof a) return !1;
                    var c = [
                        6
                    ];
                    c[1] = this.isLogin;
                    c[2] = String(a.message || '').substring(0, 300);
                    c[3] = String(a.value || '').substring(0, 300);
                    c[4] = String(a.type || '').substring(0, 50);
                    c[5] = String(a.dom || '').substring(0, 100);
                    c[6] = String(a.form || '').substring(0, 100);
                    c[7] = parseInt(a.count || 0, 10) || 0;
                    this.sendData({
                        c: !0,
                        d: {
                            ub: c
                        },
                        callback: b || !1
                    })
                },
                _trackMatrix: function(a, b, d, e, f) {
                    e = ('number' == typeof e ? e : c.now());
                    var h = 0;
                    'string' === typeof a && 'object' === typeof b && 'number' === typeof d && (a = {
                        name: a,
                        tags: b,
                        value: d,
                        ts: e
                    }, a = [
                        [1,
                            'matrix'
                        ],
                        this.getCommon(), [
                            a
                        ]
                    ], a = 'ac=a&d=' + c.compress(c.JSON.stringify(a)), c.send(a), h = 1);
                    c.isFunction(f) && f(h)
                },
                _trackMetric: function(a) {
                    var b = 0;
                    if ('object' === typeof a && (a = c.extend({
                                name: '',
                                tag: !1,
                                value: 0,
                                callback: w,
                                sample: 100
                            }, a), (b = h.get_('vid')) &&
                            !(c.hash(b) % 100 > 1 * a.sample) && 'string' === typeof a.name && 'object' === typeof a.tag && 'number' === typeof a.value)) {
                        a: {
                            var b = a.tag,
                                d = c.keys(b),
                                e = d.length;
                            if (8 < e) b = 8;
                            else {
                                for (var f = 0; f < e; f++) {
                                    var q = b[d[f]];
                                    if ('string' == typeof q) b[d[f]] = q.substring(0, 200);
                                    else if ('number' != typeof q) {
                                        b = 110;
                                        break a
                                    }
                                }
                                b = 1
                            }
                        }
                        1 == b && (b = {
                            name: a.name,
                            tags: a.tag,
                            value: a.value,
                            ts: c.now() || 0
                        }, b = [
                            [1,
                                'matrix'
                            ],
                            this.getCommon(), [
                                b
                            ]
                        ], b = 'ac=a&d=' + c.compress(c.JSON.stringify(b)), c.send(b), b = 1);
                        c.isFunction(a.callback) && a.callback(b)
                    }
                }
            };
            var h = new t,
                B = new function() {
                    this.push = function(a) {
                        for (var b = arguments, c = 0, e = b.length, f = 0; f < e; f++) try {
                            if ('function' === typeof b[f]) b[f]();
                            else {
                                var q = b[f][0];
                                '_' == q.substring(0, 1) && h[q].apply(h, b[f].slice(1))
                            }
                        } catch (n) {
                            c++
                        }
                        return c
                    }
                },
                K = alert,
                E = 0;
            (function() {
                try {
                    f.alert = function(a) {
                        E++;
                        B.push(['_trackUserBlock', {
                            message: a,
                            value: 'alert',
                            type: 'function.alert',
                            dom: '',
                            form: '',
                            count: E
                        }]);
                        K(a)
                    }
                } catch (a) {}
            })();
            var F = f.__bfi;
            if (F && c.isArray(F))
                for (var L = f.__bfi.length, t = 0; t < L; t++) B.push(f.__bfi[t]);
            else f.__bfi = [];
            f.__bfi.push = B.push;
            f.$_bf._getFullPV = function() {
                return h.get_('fullpv')
            };
            f.$_bf.tracklog = function(a, b, c) {
                h._tracklog(a, b, c)
            };
            f.$_bf.trackError = function(a, b) {
                h._trackError(a, b)
            };
            f.$_bf._getStatus = function() {
                return {
                    vid: h.get_('vid'),
                    sid: h.get_('sid'),
                    pvid: h.get_('pvid'),
                    pid: h.getPid(),
                    abtest: h.getABtest(),
                    pv: h.isPVSend,
                    ps: h.isPSSend
                }
            };
            f.$_bf.asynRefresh = function(a, b) {
                h._asynRefresh(a, b)
            };
            var z = 1,
                A = !1,
                G = function() {
                    if (!A) {
                        if (c._union_) {
                            if ('wait' != c.$v('page_id')) {
                                if (0 != h.getPid() || 'complete' === document.readyState) return A = !0,
                                    h.bindSend();
                                var a = !1;
                                try {
                                    a = null == f.frameElement && document.documentElement
                                } catch (b) {}
                                if (a && a.doScroll) try {
                                    return a.doScroll('left'),
                                        A = !0,
                                        h.bindSend()
                                } catch (d) {}
                            }
                            z = 1
                        }
                        5 < z && (c._union_ = !0);
                        if (40 < z) return h.bindSend();
                        z++;
                        setTimeout(G, 500)
                    }
                };
            G();
            // c.on(f, 'beforeunload', function() {
            //     h.bindSend()
            // });
            if ('null' == c.type(f.onerror)) {
                var H = {};
                f.onerror = function() {
                    if ('undefined' == typeof cQuery || !cQuery.config || !cQuery.config('allowDebug')) try {
                        var a = arguments,
                            b = {
                                message: '' + a[0],
                                file: '' + a[1],
                                line: a[2],
                                category: 'inner-error',
                                framework: 'normal',
                                time: c.now() - h.enterTime,
                                repeat: 1
                            },
                            d = b.message + b.file + b.line;
                        H[d] || (h._trackError(b), H[d] = !0)
                    } catch (e) {}
                }
            }(function(a) {
                function b(a, b) {
                    var c = null;
                    return function() {
                        var d = this,
                            e = arguments;
                        clearTimeout(c);
                        c = setTimeout(function() {
                            a.apply(d, e)
                        }, b)
                    }
                }

                function d() {
                    this._queue = [];
                    this._init()
                }
                if (!c.ishttps && 'undefined' != typeof JSON && 'function' == typeof JSON.stringify && a.performance && a.top == a.self) {
                    var e = h.get_('vid');
                    if (!e || 10 < c.hash(e) % 100) return !1;
                    var l = document.documentElement,
                        e = '1.0.0'.trim,
                        q = (document.addEventListener ? function(a, b, c, d) {
                                a.addEventListener && a.addEventListener(b, c, d)
                            } :
                            function(a, b, c) {
                                a.attachEvent && a.attachEvent('on' + b, c)
                            });
                    e && e.call('﻿ ');
                    d.prototype = {
                        constructor: d,
                        _init: function() {
                            var c = this;
                            q(a, 'scroll', b(function(a) {
                                c.collectScroll(a)
                            }, 300));
                            q(a, 'resize', b(function(a) {
                                c.collectViewport(a)
                            }, 500));
                            q(document, 'click', function(a) {
                                c.collectClick(a);
                                c.collectChange(a)
                            }, !0);
                            this.collectViewport()
                        },
                        _sendData: function(a) {
                            var b = (c.CFG.debug ? 'http://localhost/bf.gif?ac=a&d=' :
                                    c.CFG.surl + 'bf.gif?ac=a&d='),
                                b = b + (c.compress(a) + '&jv=1.0.0'),
                                d = (new Image);
                            d.onload = function() {
                                d = d.onload = null
                            };
                            d.src = b
                        },
                        _getTagIndex: function(a) {
                            var b = 0;
                            if (a.parentNode)
                                for (var c = a.parentNode.firstChild; c && c != a;) 1 == c.nodeType && c.tagName == a.tagName && b++,
                                    c = c.nextSibling;
                            return (0 < b ? '[' + ++b + ']' : '')
                        },
                        _getXpath: function(a) {
                            for (var b = [], c = 0, d; a && 9 != a.nodeType;) {
                                var e = a.nodeName + this._getTagIndex(a) + ((a.id ? '[@id=\'' + a.id + '\']' : ''));
                                if (d = a.getAttribute('block')) e += '[@block=\'' + d + '\']';
                                b[c++] = e;
                                a = a.parentNode
                            }
                            return b.reverse().join('/')
                        },
                        _push: function(a, b, c) {
                            this._queue.push({
                                action: a,
                                xpath: b,
                                ts: c || +new Date
                            });
                            this._checkSend()
                        },
                        _checkSend: function(a) {
                            if (a || 5 < this._queue.length) a = [
                                    [1,
                                        'useraction'
                                    ],
                                    [
                                        h.pid,
                                        h.get_('vid'),
                                        h.get_('sid'),
                                        h.get_('pvid')
                                    ],
                                    this._queue
                                ],
                                this._sendData(JSON.stringify(a)),
                                this._queue = []
                        },
                        _getFocus: function() {
                            var a = document.activeElement;
                            return (a && (a.type || a.href || ~a.tabIndex) ? a : null)
                        },
                        collectChange: function(a) {
                            this._getFocus()
                        },
                        collectViewport: function(a) {
                            this._push('viewport', 'HTML/BODY' + ('[@w=\'' + (l.clientWidth ||
                                document.body.clientWidth) + '\'][h=\'' + (l.clientHeight || document.body.clientHeight) + '\']'))
                        },
                        collectScroll: function(a) {
                            a = Math.max(l.scrollLeft, document.body.scrollLeft);
                            var b = Math.max(l.scrollTop, document.body.scrollTop);
                            this._push('scroll', 'HTML/BODY' + ('[@x=\'' + a + '\'][@y=\'' + b + '\']'))
                        },
                        collectClick: function(a) {
                            var b = a.target || a.srcElement,
                                c = b.nodeName.toUpperCase();
                            if (b.getBoundingClientRect) {
                                var d = b.getBoundingClientRect(),
                                    e = '',
                                    f = Math.max(l.scrollLeft, document.body.scrollLeft) || 0,
                                    h = Math.max(l.scrollTop, document.body.scrollTop) || 0,
                                    n = a.pageX || a.clientX + f || 0;
                                a = a.pageY || a.clientY + h || 0;
                                var q = parseInt((l.clientWidth || document.body.clientWidth) / 2, 10) || 0,
                                    r = 0,
                                    p = 0;
                                ('SELECT' == c && 0 > a - d.top - h ? (r = n, p = a - h, e += '[@x=\'' + parseInt(n + d.left + f - q, 10) + '\'][@y=\'' + parseInt(a + d.top, 10) + '\']') : (r = parseInt(n - d.left - f, 10), p = parseInt(a - d.top - h, 10), e += '[@x=\'' + (n - q) + '\'][@y=\'' + a + '\']'));
                                e = e + ('[@rx=\'' + r + '\']') + ('[@ry=\'' + p + '\']');
                                0 < r && 0 < p && this._push('click', this._getXpath(b) + e)
                            }
                        }
                    };
                    var n = new d;
                    c.on(f, 'beforeunload', function() {
                        n._checkSend(!0)
                    })
                }
            })(f);
            console.log("h.get_('vid'):"+h.get_('vid'))
            var start = function() {
                var a = h.get_('vid');
                console.log("vid:"+a);
                if (a && !(90 > c.hash(a) % 100 && -480 == (new Date).getTimezoneOffset())) try {
                    if ('performance' in f && 'getEntriesByType' in f.performance && f.performance.getEntriesByType('resource') instanceof Array) {
                        var b = {
                                'download.ctrip.com': !0,
                                'images4.c-ctrip.com': !0,
                                'webresource.c-ctrip.com': !0,
                                'dimg04.c-ctrip.com': !0,
                                'dimg02.c-ctrip.com': !0,
                                'youresource.c-ctrip.com': !0,
                                'ubt2.test.sh.ctriptravel.com': !0
                            },
                            d = (/^(?:([^:\/?#]+):)?(?:\/\/()(?:(?:()(?:([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?))?()(?:(()(?:(?:[^?#\/]*\/)*)()(?:[^?#]*))(?:\?([^#]*))?(?:#(.*))?)/),
                            e = 'source scheme authority userInfo user pass host port relative path directory file query fragment'.split(' ');
                        c.on(f, 'load', function() {
                            for (var a = f.performance.getEntriesByType('resource'), c = a.length, g = [], m = 0; m < c; m++) {
                                var k = a[m],
                                    l;
                                l = k.name;
                                if ('string' !== typeof l) l = '';
                                else {
                                    l = d.exec(l);
                                    for (var p = {}, t = 14; t--;) l[t] && (p[e[t]] = l[t]);
                                    l = p
                                }(p = 'string' != typeof l.host) || ((l = !b[l.host]) || (l = (0 == k.connectStart || 6 > k.connectEnd - k.connectStart || 6 > k.responseEnd - k.responseStart || k.domainLookupStart == k.domainLookupEnd &&
                                    k.domainLookupEnd == k.fetchStart && k.fetchStart == k.connectStart && k.fetchStart == k.connectEnd ? !0 : !1)), p = l);
                                if (!p && (g.push({
                                        entryType: k.entryType || '',
                                        initiatorType: k.initiatorType || '',
                                        name: k.name,
                                        nextHopProtocol: '',
                                        startTime: k.startTime || 0,
                                        redirectStart: k.redirectStart || 0,
                                        redirectEnd: k.redirectEnd || 0,
                                        fetchStart: k.fetchStart || 0,
                                        domainLookupStart: k.domainLookupStart || 0,
                                        domainLookupEnd: k.domainLookupEnd || 0,
                                        connectStart: k.connectStart || 0,
                                        connectEnd: k.connectEnd || 0,
                                        secureConnectionStart: k.secureConnectionStart ||
                                            0,
                                        requestStart: k.requestStart || 0,
                                        responseStart: k.responseStart || 0,
                                        responseEnd: k.responseEnd || 0,
                                        transferSize: k.transferSize || 0,
                                        encodedBodySize: k.encodedBodySize || 0,
                                        decodedBodySize: k.decodedBodySize || 0
                                    }), 2 < g.length)) break
                            }
                            0 < g.length && h.pack(g, 'restiming')
                        })
                    }
                } catch (l) {}
            }
            start();
        }
     
}
cookieFun(window);

