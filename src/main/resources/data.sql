INSERT INTO evaluator (id, name, email, created_at, updated_at)
SELECT 1, 'João Silva', 'joao.silva@empresa.com', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluator WHERE id = 1);
INSERT INTO evaluator (id, name, email, created_at, updated_at)
SELECT 2, 'Maria Souza', 'maria.souza@empresa.com', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluator WHERE id = 2);
INSERT INTO evaluator (id, name, email, created_at, updated_at)
SELECT 3, 'Carlos Lima', 'carlos.lima@empresa.com', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluator WHERE id = 3);
SELECT setval('evaluator_id_seq', (SELECT MAX(id) FROM evaluator), TRUE);

INSERT INTO evaluated (id, name, feedback, evaluator_id, created_at, updated_at)
SELECT 101, 'Ana Gerente Administrativo', 'Experiente em gestão de equipes e processos.', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluated WHERE id = 101);
INSERT INTO evaluated (id, name, feedback, evaluator_id, created_at, updated_at)
SELECT 102, 'Bruno Gerente Técnico', 'Especialista em desenvolvimento de software e infraestrutura.', 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluated WHERE id = 102);
INSERT INTO evaluated (id, name, feedback, evaluator_id, created_at, updated_at)
SELECT 103, 'Sofia Gerente de Pessoas', 'Habilidade excepcional em comunicação e desenvolvimento pessoal.', 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluated WHERE id = 103);
SELECT setval('evaluated_id_seq', (SELECT MAX(id) FROM evaluated), TRUE);


INSERT INTO evaluation (id, title, description, start_date, end_date, evaluated_id, evaluator_id, manager_type, created_at, updated_at)
SELECT 1, 'Avaliação Anual - Ana G. Adm.', 'Avaliação de desempenho geral da Ana.', '2024-01-01 09:00:00'::timestamp, '2024-12-31 17:00:00'::timestamp, 101, 1, 'ADMINISTRATIVE', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluation WHERE id = 1);
INSERT INTO evaluation (id, title, description, start_date, end_date, evaluated_id, evaluator_id, manager_type, created_at, updated_at)
SELECT 2, 'Avaliação Projeto Alfa - Bruno G. Téc.', 'Desempenho no Projeto Alfa.', '2025-01-01 09:00:00'::timestamp, '2025-03-31 17:00:00'::timestamp, 102, 2, 'TECHNICAL', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluation WHERE id = 2);
INSERT INTO evaluation (id, title, description, start_date, end_date, evaluated_id, evaluator_id, manager_type, created_at, updated_at)
SELECT 3, 'Avaliação de Liderança - Sofia G. Pessoas', 'Foco em habilidades de liderança e comunicação.', '2024-07-01 09:00:00'::timestamp, '2024-12-31 17:00:00'::timestamp, 103, 3, 'PERSONAL', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluation WHERE id = 3);
INSERT INTO evaluation (id, title, description, start_date, end_date, evaluated_id, evaluator_id, manager_type, created_at, updated_at)
SELECT 4, 'Feedback Projeto Beta - Ana G. Adm.', 'Feedback sobre participação no Projeto Beta.', '2025-02-01 09:00:00'::timestamp, '2025-02-28 17:00:00'::timestamp, 101, 2, 'ADMINISTRATIVE', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM evaluation WHERE id = 4);
SELECT setval('evaluation_id_seq', (SELECT MAX(id) FROM evaluation), TRUE);


INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 1, 'Visão de Oportunidades/Inovações', 'Elabora uma boa visão de oportunidades/inovações para o grupo e para a companhia', 'ADMINISTRATIVE', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 1);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 2, 'Facilitador de Mudanças', 'Atua como facilitador para grandes mudanças que ocorrem no grupo e/ou na companhia', 'ADMINISTRATIVE', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 2);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 3, 'Conhecimento de Informações Relevantes', 'Apresenta conhecimento sobre as informações relevantes ao trabalho de cada membro equipe', 'TECHNICAL', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 3);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 4, 'Sensibilidade a Diferenças Individuais', 'Possui sensibilidade e discernimento das diferenças individuais', 'PERSONAL', 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 4);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 5, 'Boas Ideias para Tarefas Técnicas', 'Apresenta boas idéias de como realizar tarefas na sua área de atuação específica', 'TECHNICAL', 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 5);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 6, 'Entendimento de Técnicas e Métodos', 'Tem um bom entendimento das técnicas e métodos aplicados em seu trabalho', 'TECHNICAL', 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 6);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 7, 'Promove Avaliações Críticas', 'Promove avaliações críticas buscando o bom e o factível/realizável', 'PERSONAL', 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 7);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 8, 'Incentivo e Encorajamento', 'Promove incentivo e encorajamento para as tarefas realizadas pela equipe', 'PERSONAL', 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 8);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 9, 'Reconhecimento por Tarefa Realizada', 'Promove reconhecimento por uma tarefa bem realizada', 'PERSONAL', 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 9);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 10, 'Comunicação de Prioridades Estratégicas', 'Comunica as prioridades estratégicas de sua divisão/grupo/departamento', 'ADMINISTRATIVE', 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 10);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 11, 'Cria Clima Produtivo', 'Cria o clima produtivo necessário para a condução das tarefas', 'ADMINISTRATIVE', 4, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 11);
INSERT INTO characteristic (id, name, description, skill_category, evaluation_id, created_at, updated_at)
SELECT 12, 'Conduz Pessoas a Trabalhar Juntas', 'Conduz pessoas a trabalharem bem juntas', 'PERSONAL', 4, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM characteristic WHERE id = 12);
SELECT setval('characteristic_id_seq', (SELECT MAX(id) FROM characteristic), TRUE);


INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 1, 5, '', 1, 1, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 1);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 2, 4, '', 2, 1, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 2);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 3, 3, '', 3, 1, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 3);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 4, 4, '', 4, 1, 1, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 4);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 5, 5, '', 5, 2, 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 5);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 6, 5, '', 6, 2, 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 6);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 7, 4, '', 7, 2, 2, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 7);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 8, 5, '', 8, 3, 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 8);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 9, 5, '', 9, 3, 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 9);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 10, 4, '', 10, 3, 3, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 10);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 11, 4, '', 11, 2, 4, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 11);
INSERT INTO rating (id, score, comment, characteristic_id, evaluator_id, evaluation_id, created_at, updated_at)
SELECT 12, 3, '', 12, 2, 4, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM rating WHERE id = 12);
SELECT setval('rating_id_seq', (SELECT MAX(id) FROM rating), TRUE);