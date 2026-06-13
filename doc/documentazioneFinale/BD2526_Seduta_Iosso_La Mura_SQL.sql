--
-- PostgreSQL database dump
--

\restrict 5cFZI6MgbfQWXbRdZw7NbHDW1MZMEWWZxjnj3o6TfgjfusRdkqXqzi5aphwYvuH

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: richiestaattiva(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.richiestaattiva() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	-- Verifica se lo studente ha gi… una richiesta in stato '?' o 'V'
	IF EXISTS(
		SELECT *
		FROM RICHIESTA AS R
		WHERE R.Login = NEW.Login 
		  AND R.Stato <> 'X' 
		  AND R.ID_Ri <> COALESCE(NEW.ID_Ri, -1)
	)
	THEN RAISE EXCEPTION 'Altra richiesta ancora attiva/in valutazione/accettata';
	END IF;
	
	RETURN NEW;
END;	
$$;


ALTER FUNCTION public.richiestaattiva() OWNER TO postgres;

--
-- Name: richiestaincorso(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.richiestaincorso() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF EXISTS(
		SELECT *
		FROM Richiesta AS R
		WHERE R.ID_Ri <> NEW.ID_Ri AND R.ID_Ti = NEW.ID_Ti AND R.Login = NEW.Login AND ((NEW.Stato != 'X' AND NEW.Data<R.Data) OR(R.Stato!=X AND R.Data<NEW.Data))
		)
		THEN RAISE EXCEPTION 'Richiesta gi… esistente sotto altro ID';
	END IF;
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.richiestaincorso() OWNER TO postgres;

--
-- Name: richiestaunique(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.richiestaunique() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF EXISTS(
			SELECT *
			FROM Richiesta AS R
			WHERE R.ID_Ri <> NEW.ID_Ri AND R.ID_Ti = NEW.ID_Ti AND R.Data = NEW.Data AND R.Login = NEW.Login
		)
		THEN RAISE EXCEPTION 'Richiesta gi… esistente sotto altro ID';
	END IF;
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.richiestaunique() OWNER TO postgres;

--
-- Name: sedutacontrollodatarichiesta(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.sedutacontrollodatarichiesta() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	data_richiesta DATE;
BEGIN
	SELECT R.Data INTO data_richiesta
	FROM TESI Te
	JOIN RICHIESTA R ON Te.ID_Ri = R.ID_Ri
	WHERE Te.ID_Te = NEW.ID_Te;

	IF NEW.Data <= data_richiesta THEN
		RAISE EXCEPTION 'Seduta precedente rispetto alla richiesta di tirocinio.';
	END IF;

	RETURN NEW;
END;	
$$;


ALTER FUNCTION public.sedutacontrollodatarichiesta() OWNER TO postgres;

--
-- Name: sedutarimuoviannullamentotesi(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.sedutarimuoviannullamentotesi() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN	
	IF NEW.Stato ='X' AND OLD.Stato = 'V' THEN
		DELETE FROM SEDUTA 
		WHERE ID_Te = NEW.ID_Te;
	END IF;
	
	RETURN NEW;
END;	
$$;


ALTER FUNCTION public.sedutarimuoviannullamentotesi() OWNER TO postgres;

--
-- Name: sedutasutesiaccettata(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.sedutasutesiaccettata() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF EXISTS(
		SELECT *
		FROM Tesi AS T
		WHERE T.ID_Te = NEW.ID_Te AND T.Stato <> 'V'
	)
	THEN RAISE EXCEPTION 'Seduta associata a tesi non accettata';
	END IF;

	RETURN NEW;
END;
$$;


ALTER FUNCTION public.sedutasutesiaccettata() OWNER TO postgres;

--
-- Name: sedutesovrapposizionedocente(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.sedutesovrapposizionedocente() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF EXISTS(
		SELECT *
		FROM SEDUTA AS S
		WHERE S.Login = NEW.Login 
		  AND S.Data = NEW.Data 
		  AND S.Ora = NEW.Ora 
		  AND S.ID_Se <> COALESCE(NEW.ID_Se, -1)
	)
	THEN RAISE EXCEPTION 'Docente gi… occupato per seduta';
	END IF;
	
	RETURN NEW;
END;	
$$;


ALTER FUNCTION public.sedutesovrapposizionedocente() OWNER TO postgres;

--
-- Name: tesirimuoviannullamentorichiesta(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.tesirimuoviannullamentorichiesta() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN	
	IF NEW.Stato ='X' AND OLD.Stato = 'V' THEN
		DELETE FROM SEDUTA 
		WHERE ID_Te IN (SELECT ID_Te FROM TESI WHERE ID_Ri = NEW.ID_Ri);
		DELETE FROM TESI 
		WHERE ID_Ri = NEW.ID_Ri;
	END IF;
	
	RETURN NEW;
END;	
$$;


ALTER FUNCTION public.tesirimuoviannullamentorichiesta() OWNER TO postgres;

--
-- Name: tesisurichiestaaccettata(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.tesisurichiestaaccettata() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF EXISTS(
		SELECT *
		FROM Richiesta as R
		WHERE R.ID_Ri = NEW.ID_Ri AND R.Stato <> 'V'			
	)
		THEN RAISE EXCEPTION 'Tesi associata a richiesta non accettata';
	END IF;
	
	RETURN NEW;
END;	
$$;


ALTER FUNCTION public.tesisurichiestaaccettata() OWNER TO postgres;

--
-- Name: tesiunique(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.tesiunique() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF EXISTS(
		SELECT *
		FROM Tesi AS T
		WHERE T.ID_Te <> NEW.ID_Te AND T.Contenuto = NEW.Contenuto
	)
	THEN RAISE EXCEPTION 'Tesi gi… esistente sotto altro ID e altra richiesta';
	END IF;
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.tesiunique() OWNER TO postgres;

--
-- Name: tirociniounique(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.tirociniounique() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF EXISTS(
		SELECT *
		FROM TIROCINIO AS T
		WHERE T.ID_Ti <> NEW.ID_Ti AND T.Nome = NEW.NOME AND T.Data = NEW.Data AND T.Login = NEW.Login	
	)
	THEN RAISE EXCEPTION 'Tirocinio gi… esistente sotto altro ID';
	END IF;
	RETURN NEW;
END;
$$;


ALTER FUNCTION public.tirociniounique() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: docente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.docente (
    nome character varying(32) NOT NULL,
    cognome character varying(32) NOT NULL,
    password character varying(64) NOT NULL,
    login character varying(32) NOT NULL,
    stato boolean DEFAULT false NOT NULL,
    email character varying(128) NOT NULL,
    coordinatore boolean,
    CONSTRAINT docenteemailcheck CHECK (((email)::text ~~ '%@%.%'::text)),
    CONSTRAINT docenteloginlength CHECK ((length((login)::text) >= 4))
);


ALTER TABLE public.docente OWNER TO postgres;

--
-- Name: richiesta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.richiesta (
    id_ri integer NOT NULL,
    data date,
    stato character(1) DEFAULT '?'::bpchar NOT NULL,
    login character varying(32) NOT NULL,
    id_ti integer NOT NULL,
    CONSTRAINT checkstatorichiesta CHECK (((stato = '?'::bpchar) OR (stato = 'V'::bpchar) OR (stato = 'X'::bpchar)))
);


ALTER TABLE public.richiesta OWNER TO postgres;

--
-- Name: richiesta_id_ri_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.richiesta_id_ri_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.richiesta_id_ri_seq OWNER TO postgres;

--
-- Name: richiesta_id_ri_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.richiesta_id_ri_seq OWNED BY public.richiesta.id_ri;


--
-- Name: seduta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seduta (
    id_se integer NOT NULL,
    data date NOT NULL,
    ora time without time zone NOT NULL,
    votofinale integer,
    id_te integer NOT NULL,
    login character varying(32) NOT NULL,
    CONSTRAINT checkvotofinaleseduta CHECK (((votofinale >= 0) AND (votofinale <= 32) AND (votofinale <> 31)))
);


ALTER TABLE public.seduta OWNER TO postgres;

--
-- Name: seduta_id_se_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seduta_id_se_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seduta_id_se_seq OWNER TO postgres;

--
-- Name: seduta_id_se_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.seduta_id_se_seq OWNED BY public.seduta.id_se;


--
-- Name: studente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.studente (
    nome character varying(32) NOT NULL,
    cognome character varying(32) NOT NULL,
    password character varying(64) NOT NULL,
    login character varying(32) NOT NULL,
    stato boolean DEFAULT false NOT NULL,
    email character varying(128) NOT NULL,
    matricola character(10) NOT NULL,
    CONSTRAINT studenteemailcheck CHECK (((email)::text ~~ '%@%.%'::text)),
    CONSTRAINT studenteloginlength CHECK ((length((login)::text) >= 4))
);


ALTER TABLE public.studente OWNER TO postgres;

--
-- Name: tesi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tesi (
    id_te integer NOT NULL,
    stato character(1) DEFAULT '?'::bpchar NOT NULL,
    contenuto character varying(2048),
    id_ri integer NOT NULL,
    CONSTRAINT checkstatotesi CHECK (((stato = '?'::bpchar) OR (stato = 'V'::bpchar) OR (stato = 'X'::bpchar)))
);


ALTER TABLE public.tesi OWNER TO postgres;

--
-- Name: tesi_id_te_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tesi_id_te_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tesi_id_te_seq OWNER TO postgres;

--
-- Name: tesi_id_te_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tesi_id_te_seq OWNED BY public.tesi.id_te;


--
-- Name: tirocinio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tirocinio (
    id_ti integer NOT NULL,
    descrizione character varying(256),
    nome character varying(64) NOT NULL,
    data date NOT NULL,
    login character varying(32) NOT NULL
);


ALTER TABLE public.tirocinio OWNER TO postgres;

--
-- Name: tirocinio_id_ti_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tirocinio_id_ti_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tirocinio_id_ti_seq OWNER TO postgres;

--
-- Name: tirocinio_id_ti_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tirocinio_id_ti_seq OWNED BY public.tirocinio.id_ti;


--
-- Name: tirocinioesterno; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tirocinioesterno (
    id_ties integer NOT NULL,
    id_ti integer NOT NULL,
    nomeazienda character varying(64) NOT NULL,
    referente character varying(64) NOT NULL
);


ALTER TABLE public.tirocinioesterno OWNER TO postgres;

--
-- Name: tirocinioesterno_id_ties_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tirocinioesterno_id_ties_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tirocinioesterno_id_ties_seq OWNER TO postgres;

--
-- Name: tirocinioesterno_id_ties_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tirocinioesterno_id_ties_seq OWNED BY public.tirocinioesterno.id_ties;


--
-- Name: richiesta id_ri; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.richiesta ALTER COLUMN id_ri SET DEFAULT nextval('public.richiesta_id_ri_seq'::regclass);


--
-- Name: seduta id_se; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seduta ALTER COLUMN id_se SET DEFAULT nextval('public.seduta_id_se_seq'::regclass);


--
-- Name: tesi id_te; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tesi ALTER COLUMN id_te SET DEFAULT nextval('public.tesi_id_te_seq'::regclass);


--
-- Name: tirocinio id_ti; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tirocinio ALTER COLUMN id_ti SET DEFAULT nextval('public.tirocinio_id_ti_seq'::regclass);


--
-- Name: tirocinioesterno id_ties; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tirocinioesterno ALTER COLUMN id_ties SET DEFAULT nextval('public.tirocinioesterno_id_ties_seq'::regclass);


--
-- Data for Name: docente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.docente (nome, cognome, password, login, stato, email, coordinatore) FROM stdin;
\.


--
-- Data for Name: richiesta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.richiesta (id_ri, data, stato, login, id_ti) FROM stdin;
\.


--
-- Data for Name: seduta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.seduta (id_se, data, ora, votofinale, id_te, login) FROM stdin;
\.


--
-- Data for Name: studente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.studente (nome, cognome, password, login, stato, email, matricola) FROM stdin;
\.


--
-- Data for Name: tesi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tesi (id_te, stato, contenuto, id_ri) FROM stdin;
\.


--
-- Data for Name: tirocinio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tirocinio (id_ti, descrizione, nome, data, login) FROM stdin;
\.


--
-- Data for Name: tirocinioesterno; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tirocinioesterno (id_ties, id_ti, nomeazienda, referente) FROM stdin;
\.


--
-- Name: richiesta_id_ri_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.richiesta_id_ri_seq', 1, false);


--
-- Name: seduta_id_se_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seduta_id_se_seq', 1, false);


--
-- Name: tesi_id_te_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tesi_id_te_seq', 1, false);


--
-- Name: tirocinio_id_ti_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tirocinio_id_ti_seq', 1, false);


--
-- Name: tirocinioesterno_id_ties_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tirocinioesterno_id_ties_seq', 1, false);


--
-- Name: docente docente_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.docente
    ADD CONSTRAINT docente_email_key UNIQUE (email);


--
-- Name: docente docente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.docente
    ADD CONSTRAINT docente_pkey PRIMARY KEY (login);


--
-- Name: richiesta richiesta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT richiesta_pkey PRIMARY KEY (id_ri);


--
-- Name: seduta seduta_id_te_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seduta
    ADD CONSTRAINT seduta_id_te_key UNIQUE (id_te);


--
-- Name: seduta seduta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seduta
    ADD CONSTRAINT seduta_pkey PRIMARY KEY (id_se);


--
-- Name: studente studente_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studente
    ADD CONSTRAINT studente_email_key UNIQUE (email);


--
-- Name: studente studente_matricola_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studente
    ADD CONSTRAINT studente_matricola_key UNIQUE (matricola);


--
-- Name: studente studente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.studente
    ADD CONSTRAINT studente_pkey PRIMARY KEY (login);


--
-- Name: tesi tesi_id_ri_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tesi
    ADD CONSTRAINT tesi_id_ri_key UNIQUE (id_ri);


--
-- Name: tesi tesi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tesi
    ADD CONSTRAINT tesi_pkey PRIMARY KEY (id_te);


--
-- Name: tirocinio tirocinio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tirocinio
    ADD CONSTRAINT tirocinio_pkey PRIMARY KEY (id_ti);


--
-- Name: tirocinioesterno tirocinioesterno_id_ti_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tirocinioesterno
    ADD CONSTRAINT tirocinioesterno_id_ti_key UNIQUE (id_ti);


--
-- Name: tirocinioesterno tirocinioesterno_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tirocinioesterno
    ADD CONSTRAINT tirocinioesterno_pkey PRIMARY KEY (id_ties);


--
-- Name: richiesta richiestaattivatrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER richiestaattivatrigger BEFORE INSERT OR UPDATE ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.richiestaattiva();


--
-- Name: richiesta richiestaincorsotrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER richiestaincorsotrigger BEFORE INSERT OR UPDATE ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.richiestaincorso();


--
-- Name: richiesta richiestauniquetrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER richiestauniquetrigger BEFORE INSERT OR UPDATE ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.richiestaunique();


--
-- Name: seduta sedutacontrollodatarichiestatrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER sedutacontrollodatarichiestatrigger BEFORE INSERT OR UPDATE ON public.seduta FOR EACH ROW EXECUTE FUNCTION public.sedutacontrollodatarichiesta();


--
-- Name: tesi sedutarimuoviannullamentotesitrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER sedutarimuoviannullamentotesitrigger AFTER UPDATE ON public.tesi FOR EACH ROW EXECUTE FUNCTION public.sedutarimuoviannullamentotesi();


--
-- Name: seduta sedutasutesiaccettatatrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER sedutasutesiaccettatatrigger BEFORE INSERT OR UPDATE ON public.seduta FOR EACH ROW EXECUTE FUNCTION public.sedutasutesiaccettata();


--
-- Name: seduta sedutesovrapposizionedocentetrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER sedutesovrapposizionedocentetrigger BEFORE INSERT OR UPDATE ON public.seduta FOR EACH ROW EXECUTE FUNCTION public.sedutesovrapposizionedocente();


--
-- Name: richiesta tesirimuoviannullamentorichiestatrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tesirimuoviannullamentorichiestatrigger AFTER UPDATE ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.tesirimuoviannullamentorichiesta();


--
-- Name: tesi tesisurichiestaaccettatatrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tesisurichiestaaccettatatrigger BEFORE INSERT OR UPDATE ON public.tesi FOR EACH ROW EXECUTE FUNCTION public.tesisurichiestaaccettata();


--
-- Name: tesi tesiuniquetrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tesiuniquetrigger BEFORE INSERT OR UPDATE ON public.tesi FOR EACH ROW EXECUTE FUNCTION public.tesiunique();


--
-- Name: tirocinio tirociniouniquetrigger; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tirociniouniquetrigger BEFORE INSERT OR UPDATE ON public.tirocinio FOR EACH ROW EXECUTE FUNCTION public.tirociniounique();


--
-- Name: richiesta richiesta_id_ti_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT richiesta_id_ti_fkey FOREIGN KEY (id_ti) REFERENCES public.tirocinio(id_ti);


--
-- Name: richiesta richiesta_login_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT richiesta_login_fkey FOREIGN KEY (login) REFERENCES public.studente(login);


--
-- Name: seduta seduta_id_te_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seduta
    ADD CONSTRAINT seduta_id_te_fkey FOREIGN KEY (id_te) REFERENCES public.tesi(id_te);


--
-- Name: seduta seduta_login_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seduta
    ADD CONSTRAINT seduta_login_fkey FOREIGN KEY (login) REFERENCES public.docente(login);


--
-- Name: tesi tesi_id_ri_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tesi
    ADD CONSTRAINT tesi_id_ri_fkey FOREIGN KEY (id_ri) REFERENCES public.richiesta(id_ri);


--
-- Name: tirocinio tirocinio_login_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tirocinio
    ADD CONSTRAINT tirocinio_login_fkey FOREIGN KEY (login) REFERENCES public.docente(login);


--
-- Name: tirocinioesterno tirocinioesterno_id_ti_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tirocinioesterno
    ADD CONSTRAINT tirocinioesterno_id_ti_fkey FOREIGN KEY (id_ti) REFERENCES public.tirocinio(id_ti);


--
-- PostgreSQL database dump complete
--

\unrestrict 5cFZI6MgbfQWXbRdZw7NbHDW1MZMEWWZxjnj3o6TfgjfusRdkqXqzi5aphwYvuH

