import { Pool, PoolClient } from 'pg';

export class DB {
  pool = new Pool({
    user: 'bcra',
    host: 'localhost',
    database: 'bcra',
    password: 'bcra123',
    port: 5432,
    connectionTimeoutMillis: 2000
  });

  async execute(sql: string, values: any) {
    const client = await this.pool.connect();
    const result = await client.query(sql, values);
    return result.rows[0];
  }
}
