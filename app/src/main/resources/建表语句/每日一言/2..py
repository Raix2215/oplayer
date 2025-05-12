import json
import os
import mysql.connector
from mysql.connector import Error


def parse_json_and_insert_to_db(file_path, db_config):
    # 连接到数据库
    try:
        connection = mysql.connector.connect(**db_config)
        cursor = connection.cursor()

        # 打开JSON文件并解析
        with open(file_path, 'r', encoding='utf-8') as file:
            data = json.load(file)

        # 提取信息并插入数据库
        sentences = []
        for item in data:
            hitokoto = item.get('hitokoto', '')
            from_source = item.get('from', '')
            sentences.append((hitokoto, from_source))

        # 每满100条插入一次数据库
        for i in range(0, len(sentences), 100):
            batch = sentences[i:i + 100]
            insert_query = """
            INSERT INTO sentences (sentence, from_source) VALUES (%s, %s)
            """
            cursor.executemany(insert_query, batch)
            connection.commit()

        cursor.close()
        connection.close()

        return sentences
    except Error as e:
        print(f"Error: {e}")
        return []


def generate_insert_sql_file(file_path, sentences):
    # 生成SQL插入语句文件
    output_file = file_path.replace('.json', '_insert.sql')
    with open(output_file, 'w', encoding='utf-8') as sql_file:
        sql_file.write("USE your_database_name;\n")
        sql_file.write("INSERT INTO sentences (sentence, from_source) VALUES \n")
        for i, (sentence, source) in enumerate(sentences):
            sql_file.write(f"('{sentence.replace("'", "''")}', '{source.replace("'", "''")}')")
            if i < len(sentences) - 1:
                sql_file.write(",\n")
            else:
                sql_file.write(";\n")
    print(f"Generated SQL file: {output_file}")


def main():
    # 数据库配置
    # 数据库连接配置
    db_config = {
        'host': '1',
        'user': '1',
        'password': '1',
        'database': '1'
    }

    # 数据文件夹路径
    data_folder = 'data'

    # 遍历文件夹中的所有JSON文件
    for file_name in os.listdir(data_folder):
        if file_name.endswith('.json'):
            file_path = os.path.join(data_folder, file_name)
            print(f"Processing file: {file_path}")
            sentences = parse_json_and_insert_to_db(file_path, db_config)
            generate_insert_sql_file(file_path, sentences)


if __name__ == "__main__":
    main()